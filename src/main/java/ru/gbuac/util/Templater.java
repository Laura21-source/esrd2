package ru.gbuac.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.google.common.io.Files;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Templater {

    public static void main(String[]args) throws Exception {


    }

    private static int getPageCount(XWPFDocument doc) throws IOException {
        ByteArrayOutputStream byteArrayOutputStreamTemp = new ByteArrayOutputStream();
        doc.write(byteArrayOutputStreamTemp);
        PDDocument pdfTemp = PDDocument.load(getPdfBytes(new ByteArrayInputStream(byteArrayOutputStreamTemp.toByteArray())).toByteArray());
        return pdfTemp.getNumberOfPages();
    }

    private static List<IfStatement> getIfStatements(String text) {
        if (text == null) {
            return null;
        }
        final String regex = "(IF\\{.*.}+THEN\\{.*.}+ELSE\\{.*.})|(IF\\{.*.}+THEN\\{.*.})";
        final Pattern pattern;
        pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(text);
        List<IfStatement> ifStatements = new ArrayList<>();
        while (matcher.find()) {
            ifStatements.add(parseIfStatements(matcher.group()));
        }
        return ifStatements;
    }

    private static IfStatement parseIfStatements(String text) {
        if (text == null) {
            return null;
        }
        final String regex = "\\{(.*?)\\}";
        final Pattern pattern;
        pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(text);
        return new IfStatement(matcher.find() ? matcher.group(1) : "", matcher.find() ? matcher.group(1) : "", matcher.find() ? matcher.group(1) : "", text);
    }

    public static ByteArrayOutputStream fillTagsByDictionary(String templatePath, Map<String, String> simpleTags,
                                                             Map<String, TaggedTable> taggedTables,
                                     Boolean isPDF) throws Exception {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(templatePath));

        // Замена тэгов щаблона значениями по словарю
        for (XWPFParagraph p: doc.getParagraphs()) {
            String text = p.getText();
            for (Map.Entry<String,String> entry : simpleTags.entrySet()) {
                text = text.replace("<"+entry.getKey()+">", Optional.ofNullable(entry.getValue()).orElse(""));
            }
            changeText(p, text);
        }

        // Замена тэгов в таблицах, которые есть в taggedTables
        if (taggedTables.size() != 0 && doc.getTables().size() != 0) {
            // Перебор всех таблиц в документе
            for (int i = 0; i <doc.getTables().size(); i++)
            {
                XWPFTable table = doc.getTableArray(i);
                // Ищем, имеет ли текущая таблица каике-либо тэги для замены
                XWPFTableRow lastRow = table.getRows().get(table.getNumberOfRows() - 1);
                String firstCellTag = lastRow.getTableCells().get(0).getText();

                if (taggedTables.containsKey(TagUtil.getTableTag(firstCellTag))) {
                    // Получаем структуру с тэгами для замены в таблице
                    TaggedTable taggedTable = taggedTables.get(TagUtil.getTableTag(firstCellTag));

                    // Перебираем строки в структуре и в зависимости от количества в ней строк, генерим строки в таблице
                    // в документе Word
                    for (int row = 0; row < taggedTable.getRows().size(); row++) {
                        lastRow = table.getRows().get(table.getNumberOfRows() - 1);
                        CTRow ctrowTemplate = CTRow.Factory.parse(lastRow.getCtRow().newInputStream());
                        XWPFTableRow newRow = new XWPFTableRow(ctrowTemplate, table);

                        for (int cell = 0; cell < newRow.getTableCells().size(); cell++) {
                            XWPFTableCell cellObj = newRow.getTableCells().get(cell);
                            for (XWPFParagraph paragraph : cellObj.getParagraphs()) {
                                String text = paragraph.getText();
                                for (Map.Entry<String, String> entry : taggedTable.getRows().get(row).getCellsTags().entrySet()) {
                                    text = text.replace("<" + entry.getKey() + ">", Optional.ofNullable(entry.getValue()).orElse(""));
                                }
                                text = text.replace("<[" + taggedTable.getTableName() + "]Sequence>", String.valueOf(row + 1));
                                text = text.replace("  ", " ").replace(" ,", ",");
                                changeText(paragraph, text);
                            }
                        }
                        table.addRow(newRow, table.getNumberOfRows() - 1);
                    }
                    table.removeRow(table.getNumberOfRows() - 1);
                }
            }
        }

        // Замена тэгов щаблона значениями по словарю
        for (XWPFParagraph p: doc.getParagraphs()) {
            String text = p.getText();
            for (IfStatement ifStatement : getIfStatements(text)) {
                String[] cmpValues = ifStatement.condition.split("=");
                if (cmpValues.length == 0) {
                    cmpValues = ifStatement.condition.split("~");
                }
                if (cmpValues.length == 0) {
                    continue;
                }
                if ((ifStatement.condition.contains("=") && cmpValues[0].equals(cmpValues[1])) ||
                        (ifStatement.condition.contains("~") && cmpValues[0].contains(cmpValues[1]))) {
                    text = text.replace(ifStatement.fullText, ifStatement.getThenVal());
                } else {
                    text = text.replace(ifStatement.fullText, ifStatement.getElseVal());
                }
            }
            changeText(p, text);
        }

        // Если количество страниц больше одной, то делаем полную подпись
        if (getPageCount(doc) > 1) {
            simpleTags.put("SignerPosition", simpleTags.get("SignerFullPosition"));
            //doc.getFooterList().get(0).removeTable(doc.getFooterList().get(0).getTables().get(0));
        }

        // Замена тэгов в таблицах
        for (int i = 0; i <doc.getTables().size(); i++) {
            List<XWPFTableRow> rows = doc.getTableArray(i).getRows();
            for (int row = 0; row < rows.size(); row++) {
                List<XWPFTableCell> cells = rows.get(row).getTableCells();
                for (int cell = 0; cell < cells.size(); cell++) {
                    for (XWPFParagraph p: cells.get(cell).getParagraphs()) {
                        String text = p.getText();
                        for (Map.Entry<String,String> entry : simpleTags.entrySet()) {
                            text = text.replace("<"+entry.getKey()+">", Optional.ofNullable(entry.getValue()).orElse(""));
                        }
                        text = text.replace("  ", " ").replace(" ,", ",");
                        changeText(p, text);
                    }
                }
            }
        }

        for (XWPFParagraph p: doc.getParagraphs()) {
            if (p.getText().contains("<Sizer>")) {
                changeText(p, "");
                int line = 0;
                int pageCount = getPageCount(doc);
                while (true) {
                    p.insertNewRun(line).addCarriageReturn();
                    p.insertNewRun(++line).addCarriageReturn();
                    p.insertNewRun(++line).addCarriageReturn();
                    int newPageCount = getPageCount(doc);
                    if (newPageCount > pageCount) {
                        p.removeRun(line);
                        p.removeRun(--line);
                        p.removeRun(--line);
                        break;
                    }
                }

            }
        }


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        doc.write(byteArrayOutputStream);
        if (isPDF) {
            byteArrayOutputStream = getPdfBytes(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        }
        return byteArrayOutputStream;
    }


    private static void changeText(XWPFParagraph p, String newText) {
        List<XWPFRun> runs = p.getRuns();
        if (runs.size() != 0) {
            for (int i = runs.size() - 1; i > 0; i--) {
                p.removeRun(i);
            }
            XWPFRun run = runs.get(0);
            run.setText(newText, 0);
        }
    }

    private static ByteArrayOutputStream getPdfBytes(InputStream docxInputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IConverter converter = LocalConverter.builder().build();
        converter
                .convert(docxInputStream).as(DocumentType.DOCX)
                .to(byteArrayOutputStream).as(DocumentType.PDF)
                .prioritizeWith(1000).execute();
        return byteArrayOutputStream;
    }
}
