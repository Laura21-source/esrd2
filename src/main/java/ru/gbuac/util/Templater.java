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
        String d = "IF{mgffgr}THEN{sds}ELSE{SDS}dsd23232 IF{myvar}THEN{sds}\n\n"
                + "myVar\n\n"
                + "erwrwerw IF{вавав}THEN{dвавffdf}ELSE{Установить тариф}wrwrwr\n\n"
                + "myVar1IF{myvar}THEN{sd\n\n"
                + "s}ELSE{sds}dqd\n\n"
                + "myVar_1\n\n"
                + "1var\n\n"
                + "IF{не утратил=утратил}THEN{3. Приз\n"
                + "нать утратившим су с 1 январ\n\n"
                + "я 2019 г. пункт 2 приказа Департамента ономич\n"
                + "рода Моквы от 21 ноября 2017 г. №\n\n\n"
                + " 283-ТР «Об установлении олгосроч\n"
                + "ых арифовна трартировку\n"
                + " воды и транспортировку сточных вод Международный аэропорт «Внуково на 2018-2020 годы».}\n\n"
                + " \n\n"
                + " \n\n"
                + "2var{myvar}THEN{sds}\n\n"
                + "COOL_VAR{myvar}THEN{sds}IABLE";
        List<IfStatement> ifStatements = getIfStatements(d);
        int df = 0;


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
        final String regex = "(IF\\{(.*?|.*[\\s\\S]*?)\\}THEN\\{(.*?|.*[\\s\\S]*?)\\}(ELSE\\{(.*?|.*[\\s\\S]*?)\\})?)";
        final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(text);
        List<IfStatement> ifStatements = new ArrayList<>();
        while (matcher.find()) {
//            System.out.println("Full match: " + matcher.group(0));
//            for (int i = 1; i <= matcher.groupCount(); i++) {
//                System.out.println("Group " + i + ": " + matcher.group(i));
//            }
            ifStatements.add(new IfStatement(matcher.group(2) != null ? matcher.group(2) : "",
                    matcher.group(3) != null ? matcher.group(3) : "",
                    matcher.group(5) != null ? matcher.group(5) : "",
                    matcher.group(1)));
        }
        return ifStatements;
    }

    public static void deleteParagraph(XWPFParagraph p) {
        XWPFDocument doc = p.getDocument();
        int pPos = doc.getPosOfParagraph(p);
        doc.removeBodyElement(pPos);
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
        List<XWPFParagraph> paragraphsToDelete = new ArrayList<>();
        for (XWPFParagraph p: doc.getParagraphs()) {
            String text = p.getText();
            int startLength = text.length();
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
            // Если размер параграфа стал равен нулю, и при этом изначально он таким не был,
            // то значит его нужно добавить в список удаления
            if (startLength > text.length() && text.length() == 0) {
                paragraphsToDelete.add(p);
            } else {
                changeText(p, text);
            }
        }

        // Удаление пустых параграфов
        for (int i = 0; i < paragraphsToDelete.size(); i++) {
            deleteParagraph(paragraphsToDelete.get(i));
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
