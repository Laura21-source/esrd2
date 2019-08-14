package ru.gbuac.util;

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
                { "<Date>", "3 сентября 2019" },
                { "<Time>", "10:00" },
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


        Templater templater = new Templater();
        templater.fillTagsByDictionary("C:\\Templ.docx", simpleTags, Arrays.asList(taggedTable), "C:\\Result.docx");

    }


    public void fillTagsByDictionary(String templatePath, Map<String, String> simpleTags, List<TaggedTable> taggedTableList,
                                     String outDocPath) throws Exception {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(templatePath));

        // Замена тэгов щаблона значениями по словарю
        for (XWPFParagraph p : doc.getParagraphs()) {
            String text = p.getText();
            for (Map.Entry<String,String> entry : simpleTags.entrySet()) {
                text = text.replace(entry.getKey(), entry.getValue());
            }
            changeText(p, text);
        }

        // Замена тэгов в таблицах
        if (taggedTableList.size() != 0 && doc.getTables().size() != 0) {
            // Перебор всех таблиц в документе
            for (int i = 0; i <doc.getTables().size(); i++)
            {
                XWPFTable table = doc.getTableArray(i);
                // Ищем, имеет ли текущая таблица каике-либо тэги для замены
                XWPFTableRow lastRow = table.getRows().get(table.getNumberOfRows() - 1);
                String firstCellTag = lastRow.getTableCells().get(0).getText();
                int targetTaggedTableIndex = -1;

                // Если таблица имеет тэги для замены, то определяем, по структуре taggedTableList, где у нас хранятся
                // для нее тэги. Запоминаем индекс таблицы в targetTaggedTableIndex
                for (int j = 0; j < taggedTableList.size(); j++) {
                    if (firstCellTag.contains("<"+taggedTableList.get(0).getTableName())) {
                        targetTaggedTableIndex = j;
                        break;
                    }
                }

                // Получаем структуру с тэгами для замены в таблице
                TaggedTable taggedTable = taggedTableList.get(targetTaggedTableIndex);

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
                            for (Map.Entry<String,String> entry : taggedTable.getRows().get(row).getCellsTags().entrySet()) {
                                text = text.replace("<" + taggedTable.getTableName() + "." + entry.getKey() + ">", entry.getValue());
                            }
                            changeText(paragraph, text);
                        }
                    }
                    table.addRow(newRow, table.getNumberOfRows() - 1);
                }
                table.removeRow(table.getNumberOfRows() - 1);
            }
        }
        doc.write(new FileOutputStream(outDocPath));
    }

    private void changeText(XWPFParagraph p, String newText) {
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
