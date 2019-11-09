package ru.gbuac.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import com.google.common.io.Files;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.*;
import org.docx4j.XmlUtils;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Templater {

    public static void main(String[]args) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        String html = "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"662\">\n" +
                "    <tbody>\n" +
                "        <tr>\n" +
                "            <td width=\"38\">\n" +
                "                <p align=\"center\">\n" +
                "                    № п/п\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\">\n" +
                "                <p align=\"center\">\n" +
                "                    Наименование потребителей\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p align=\"center\">\n" +
                "                    Период действия тарифа\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    Тарифы на питьевую воду, руб./куб. м\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    Тарифы на водоотведение хозяйственно-бытовых сточных вод,\n" +
                "                    (руб./куб. м)\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    Тарифы на водоотведение поверхностных сточных вод,\n" +
                "                    (руб./куб. м)\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"38\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    1.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    население (с НДС)\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.01.2018 по 30.06.2018\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    36,52\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    38,06\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    88,18\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.07.2018 по 31.12.2018\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    36,52\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    38,06\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    88,18\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"38\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    2.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    прочие потребители*\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.01.2018 по 30.06.2018\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    30,95\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    32,25\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    74,73\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.07.2018 по 31.12.2018\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    30,95\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    32,25\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    74,73\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"38\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    3.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    население (с НДС)\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.01.2019 по 30.06.2019\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    36,52\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    38,06\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    87,57\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.07.2019 по 31.12.2019\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    37,29\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    39,07\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    87,57\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"38\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    4.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    прочие потребители*\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.01.2019 по 30.06.2019\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    30,95\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    32,25\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    74,21\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.07.2019 по 31.12.2019\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    31,60\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    33,11\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    74,21\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"38\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    5.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    население (с НДС)\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.01.2020 по 30.06.2020\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    37,21\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    37,87\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    86,94\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.07.2020 по 31.12.2020\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    37,21\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    37,87\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    86,94\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"38\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    6.\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"106\" rowspan=\"2\">\n" +
                "                <p align=\"center\">\n" +
                "                    прочие потребители*\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.01.2020 по 30.06.2020\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    31,53\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    32,09\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    73,68\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        <tr>\n" +
                "            <td width=\"183\">\n" +
                "                <p>\n" +
                "                    с 01.07.2020 по 31.12.2020\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"107\">\n" +
                "                <p align=\"center\">\n" +
                "                    31,53\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"104\">\n" +
                "                <p align=\"center\">\n" +
                "                    32,09\n" +
                "                </p>\n" +
                "            </td>\n" +
                "            <td width=\"123\">\n" +
                "                <p align=\"center\">\n" +
                "                    73,68\n" +
                "                </p>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </tbody>\n" +
                "</table>";
        map.put("Table.DrinkWater", html);
        map.put("Question", "Вопрос");
        ByteArrayOutputStream baos = fillTagsByDictionary("C:\\test\\prikaz.docx", map, new HashMap<>(), false);
        baos.writeTo(new FileOutputStream("C:\\test\\saved.docx"));
    }

    private static int getPageCount(XWPFDocument doc) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStreamTemp = new ByteArrayOutputStream()) {
            doc.write(byteArrayOutputStreamTemp);
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStreamTemp.toByteArray())) {
                PDDocument pdfTemp = PDDocument.load(getPdfBytes(byteArrayInputStream).toByteArray());
                return pdfTemp.getNumberOfPages();
            }
        }
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

    //a wrapper class for the  htmlDoc /word/htmlDoc#.html in the *.docx ZIP archive
    //provides methods for manipulating the HTML
    //TODO: We should *not* using String methods for manipulating HTML!
    private static class MyXWPFHtmlDocument extends POIXMLDocumentPart {

        private String html;
        private String id;

        private MyXWPFHtmlDocument(PackagePart part, String id) throws Exception {
            super(part);
            this.html = "<!DOCTYPE html><html><head><style></style><title>HTML import</title></head><body></body>";
            this.id = id;
        }

        private String getId() {
            return id;
        }

        private String getHtml() {
            return html;
        }

        private void setHtml(String html) {
            this.html = html;
        }

        @Override
        protected void commit() throws IOException {
            PackagePart part = getPackagePart();
            OutputStream out = part.getOutputStream();
            Writer writer = new OutputStreamWriter(out, "UTF-8");
            writer.write(html);
            writer.close();
            out.close();
        }

    }

    //the XWPFRelation for /word/htmlDoc#.html
    private final static class XWPFHtmlRelation extends POIXMLRelation {
        private XWPFHtmlRelation() {
            super(
                    "text/html",
                    "http://schemas.openxmlformats.org/officeDocument/2006/relationships/aFChunk",
                    "/word/htmlDoc#.html");
        }
    }

    //a method for creating the htmlDoc /word/htmlDoc#.html in the *.docx ZIP archive
    //String id will be htmlDoc#.
    private static MyXWPFHtmlDocument createHtmlDoc(XWPFDocument document, String id) throws Exception {
        OPCPackage oPCPackage = document.getPackage();
        PackagePartName partName = PackagingURIHelper.createPartName("/word/" + id + ".html");
        PackagePart part = oPCPackage.createPart(partName, "text/html");
        MyXWPFHtmlDocument myXWPFHtmlDocument = new MyXWPFHtmlDocument(part, id);
        document.addRelation(myXWPFHtmlDocument.getId(), new XWPFHtmlRelation(), myXWPFHtmlDocument);
        return myXWPFHtmlDocument;
    }

    public static ByteArrayOutputStream fillTagsByDictionary(String templatePath, Map<String, String> simpleTags,
                                                             Map<String, TaggedTable> taggedTables,
                                     Boolean isPDF) throws Exception {
        try (XWPFDocument doc = new XWPFDocument(OPCPackage.open(templatePath)); ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            // Замена тэгов щаблона значениями по словарю
            for (XWPFParagraph p : doc.getParagraphs()) {
                String text = p.getText();
                if (!text.equals("")) {
                    for (Map.Entry<String, String> entry : simpleTags.entrySet()) {
                        if (text.contains("<" + entry.getKey() + ">")) {
                            if (entry.getKey().contains("Table")) {
                                MyXWPFHtmlDocument myXWPFHtmlDocument;

                                myXWPFHtmlDocument = createHtmlDoc(doc, entry.getKey());
                                myXWPFHtmlDocument.setHtml(myXWPFHtmlDocument.getHtml().replace("<body></body>",
                                        "<body>" +
                                                "<table>"+
                                                "<caption>A table></caption>" +
                                                "<tr><th>Name</th><th>Date</th><th>Amount</th></tr>" +
                                                "<tr><td>John Doe</td><td>2018-12-01</td><td>1,234.56</td></tr>" +
                                                "</table>" +
                                                "</body>"
                                ));

                                doc.getDocument().getBody().addNewAltChunk().setId(myXWPFHtmlDocument.getId());

                            } else {
                                text = text.replace("<" + entry.getKey() + ">", Optional.ofNullable(entry.getValue()).orElse(""));
                            }
                        }
                    }
                }
                changeText(p, text);
            }

            // Замена тэгов в таблицах, которые есть в taggedTables
            if (taggedTables.size() != 0 && doc.getTables().size() != 0) {
                // Перебор всех таблиц в документе
                for (int i = 0; i < doc.getTables().size(); i++) {
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
            for (XWPFParagraph p : doc.getParagraphs()) {
                String text = p.getText();
                int startLength = text.length();
                for (IfStatement ifStatement : getIfStatements(text)) {
                    String[] cmpValues = ifStatement.condition.split("=");
                    if (cmpValues.length == 1) {
                        cmpValues = ifStatement.condition.split("~");
                    }
                    if ((ifStatement.condition.contains("=") && cmpValues[0].equals(cmpValues[1])) ||
                            (cmpValues.length == 1 && cmpValues[0].length() > 0) ||
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
            for (int i = 0; i < doc.getTables().size(); i++) {
                List<XWPFTableRow> rows = doc.getTableArray(i).getRows();
                for (int row = 0; row < rows.size(); row++) {
                    List<XWPFTableCell> cells = rows.get(row).getTableCells();
                    for (int cell = 0; cell < cells.size(); cell++) {
                        for (XWPFParagraph p : cells.get(cell).getParagraphs()) {
                            String text = p.getText();
                            for (Map.Entry<String, String> entry : simpleTags.entrySet()) {
                                if (!entry.getKey().contains("Table")) {
                                    text = text.replace("<" + entry.getKey() + ">", Optional.ofNullable(entry.getValue()).orElse(""));
                                }
                            }
                            text = text.replace("  ", " ").replace(" ,", ",");
                            changeText(p, text);
                        }
                    }
                }
            }

            for (XWPFParagraph p : doc.getParagraphs()) {
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

            doc.write(byteArrayOutputStream);
            if (isPDF) {
                return getPdfBytes(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
            }
            return byteArrayOutputStream;
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

    private static ByteArrayOutputStream getPdfBytes(InputStream docxInputStream) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            IConverter converter = LocalConverter.builder().build();
            converter
                    .convert(docxInputStream).as(DocumentType.DOCX)
                    .to(byteArrayOutputStream).as(DocumentType.PDF)
                    .prioritizeWith(1000).execute();
            converter.shutDown();
            return byteArrayOutputStream;
        }
    }
}
