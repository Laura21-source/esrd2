package ru.gbuac.util;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Templater {

    public static void main(String[]args) throws Exception {
        Map<String, String> simpleTags = Stream.of(new String[][] {
                { "MeetingDate", "3 сентября 2019" },
                { "MeetingTime", "10:00" },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));

        Map<String, String> rowCells1 = Stream.of(new String[][] {
                { "NumTheme", "1" },
                { "Theme", "О подключении....." },
                { "AuthPerson", "Петров А.В." },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        TableRow tableRow1 = new TableRow(rowCells1);

        Map<String, String> rowCells2 = Stream.of(new String[][] {
                { "NumTheme", "2" },
                { "Theme", "Об установлении тарифа....." },
                { "AuthPerson", "Смирнов А.Г." },
        }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
        TableRow tableRow2 = new TableRow(rowCells2);

        TaggedTable taggedTable = new TaggedTable("Table1", Arrays.asList(tableRow1, tableRow2));


        //Templater templater = new Templater();
        //templater.fillTagsByDictionary("C:\\DocTemplates\\Templ.docx", simpleTags, Arrays.asList(taggedTable),
        //        "C:\\TempPDF\\PDFResult.pdf", true);

    }


    public static void fillTagsByDictionary(String templatePath, Map<String, String> simpleTags, Map<String, TaggedTable> taggedTables,
                                     String outPath, Boolean isPDF) throws Exception {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(templatePath));

        // Замена тэгов щаблона значениями по словарю
        for (XWPFParagraph p : doc.getParagraphs()) {
            String text = p.getText();
            for (Map.Entry<String,String> entry : simpleTags.entrySet()) {
                text = text.replace("<"+entry.getKey()+">", entry.getValue());
            }
            changeText(p, text);
        }

        // Замена тэгов в таблицах
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
                                    text = text.replace("<" + entry.getKey() + ">", entry.getValue());
                                }
                                text = text.replace("<[" + taggedTable.getTableName() + "]Sequence>", String.valueOf(row + 1));
                                changeText(paragraph, text);
                            }
                        }
                        table.addRow(newRow, table.getNumberOfRows() - 1);
                    }
                    table.removeRow(table.getNumberOfRows() - 1);
                }
            }
        }
        if (isPDF) {
            PdfConverter.getInstance().convert(doc, new FileOutputStream(outPath), null);
        }
        else {
            doc.write(new FileOutputStream(outPath));
        }
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
}
