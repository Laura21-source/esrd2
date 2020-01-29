package ru.gbuac.util;

import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ooxml.POIXMLRelation;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackagingURIHelper;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTAltChunk;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Templater {

    public static void main(String[]args) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        String imgPath = "C:\\test\\stamp.png";
        map.put("test.IMG", imgPath);
        ByteArrayOutputStream baos = fillTagsByDictionary("C:\\test\\povestka.docx", map, new HashMap<>(), new HashMap<>(), true);
        baos.writeTo(new FileOutputStream("C:\\test\\saved.pdf"));
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
        if (pPos == -1) {
            changeText(p, "");
        } else {
            doc.removeBodyElement(pPos);
        }
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

    private static void replaceIBodyElementWithAltChunk(XWPFDocument document, String textToFind,
                                                        MyXWPFHtmlDocument myXWPFHtmlDocument) throws Exception {
        int pos = 0;
        for (IBodyElement bodyElement : document.getBodyElements()) {
            if (bodyElement instanceof XWPFParagraph) {
                XWPFParagraph paragraph = (XWPFParagraph)bodyElement;
                String text = paragraph.getText();
                if (text != null && text.contains(textToFind)) {
                    //create XmlCursor at this paragraph
                    XmlCursor cursor = paragraph.getCTP().newCursor();
                    cursor.toEndToken(); //now we are at end of the paragraph
                    //there always must be a next start token. Either a p or at least sectPr.
                    while(cursor.toNextToken() != org.apache.xmlbeans.XmlCursor.TokenType.START);
                    //now we can insert the CTAltChunk here
                    String uri = CTAltChunk.type.getName().getNamespaceURI();
                    cursor.beginElement("altChunk", uri);
                    cursor.toParent();
                    CTAltChunk cTAltChunk = (CTAltChunk)cursor.getObject();
                    //set the altChunk's Id to reference the given MyXWPFHtmlDocument
                    cTAltChunk.setId(myXWPFHtmlDocument.getId());

                    //now remove the found IBodyElement
                    document.removeBodyElement(pos);

                    break; //break for each loop
                }
            }
            pos++;
        }
    }

    private static boolean calcAndConditionsResult(String condition) {
        String[] andBlocks = condition.split("&&");
        for (String block : andBlocks) {
            if (!checkCondition(block)) {
                return false;
            }
        }
        return true;
    }

    private static boolean calcOrConditionsResult(String condition) {
        String[] orBlocks = condition.split("\\|\\|");
        for (String block : orBlocks) {
            if (calcAndConditionsResult(block)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkCondition(String condition) {
        String[] cmpValues = condition.split("=");
        if (cmpValues.length == 1) {
            cmpValues = condition.split("~");
            if (cmpValues.length == 1) {
                cmpValues = condition.split("!=");
            }
        }
        return  (condition.contains("!=") && !cmpValues[0].equals(cmpValues[1])) ||
                (condition.contains("=") && cmpValues[0].equals(cmpValues[1])) ||
                (cmpValues.length == 1 && (cmpValues[0].equals("TRUE") || cmpValues[0].equals("!FALSE"))) ||
                (cmpValues.length == 1 && !(cmpValues[0].equals("FALSE") || cmpValues[0].equals("!TRUE"))) ||
                (condition.contains("~") && cmpValues[0].contains(cmpValues[1]));
    }

    private static void ifTagsCalculate(List<XWPFParagraph> paragraphs) {
        List<XWPFParagraph> paragraphsToDelete = new ArrayList<>();
        for (XWPFParagraph p: paragraphs) {
            String text = p.getText();
            int startLength = text.length();
            for (IfStatement ifStatement : getIfStatements(text)) {
                if (calcOrConditionsResult(ifStatement.condition)) {
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
    }

    private static CTAnchor getAnchorWithGraphic(CTDrawing drawing /*inline drawing*/ ,
                                                 String drawingDescr, boolean behind) throws Exception {

        CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();
        long width = drawing.getInlineArray(0).getExtent().getCx();
        long height = drawing.getInlineArray(0).getExtent().getCy();

        String anchorXML =
                "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                        +"simplePos=\"0\" relativeHeight=\"0\" behindDoc=\""+((behind)?1:0)+"\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
                        +"<wp:simplePos x=\"0\" y=\"0\"/>"
                        +"<wp:positionH relativeFrom=\"column\"><wp:posOffset>0</wp:posOffset></wp:positionH>"
                        +"<wp:positionV relativeFrom=\"paragraph\"><wp:posOffset>0</wp:posOffset></wp:positionV>"
                        +"<wp:extent cx=\""+width+"\" cy=\""+height+"\"/>"
                        +"<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/><wp:wrapNone/>"
                        +"<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\""+drawingDescr+"\"/><wp:cNvGraphicFramePr/>"
                        +"</wp:anchor>";

        drawing = CTDrawing.Factory.parse(anchorXML);
        CTAnchor anchor = drawing.getAnchorArray(0);
        anchor.setGraphic(graphicalobject);
        return anchor;
    }

    private static void insertImage(String imgPath, XWPFParagraph p, double decreaseWidthCoefficient,double decreaseHeightCoefficient) {
        try (FileInputStream isB = new FileInputStream(imgPath)) {
            BufferedImage bImage = ImageIO.read(isB);
            try (FileInputStream is = new FileInputStream(imgPath)) {
                XWPFRun run = p.getRuns().get(0);
                run.addBreak();
                run.addPicture(is, XWPFDocument.PICTURE_TYPE_PNG, imgPath,
                        Units.toEMU(bImage.getWidth()/decreaseWidthCoefficient),
                        Units.toEMU(bImage.getHeight()/decreaseHeightCoefficient));
                CTDrawing drawing = run.getCTR().getDrawingArray(0);
                CTAnchor anchor = getAnchorWithGraphic(drawing, imgPath, false /*not behind text*/);
                drawing.setAnchorArray(new CTAnchor[]{anchor});
                drawing.removeInline(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void replaceSimpleTags(List<XWPFParagraph> paragraphs, Map<String, String> simpleTags) {
        for (XWPFParagraph p : paragraphs) {
            String text = p.getText();
            if (!text.equals("")) {
                for (Map.Entry<String, String> entry : simpleTags.entrySet()) {
                    if (text.contains("<" + entry.getKey() + ">")) {
                        if (!entry.getKey().contains(".IMG")) {
                            text = text.replace("<" + entry.getKey() + ">", Optional.ofNullable(entry.getValue()).orElse(""));
                        } else {
                            String parentEntryValue = simpleTags.get(entry.getKey().replace(".IMG", ""));
                            if (parentEntryValue.equals("TRUE"))
                                insertImage(entry.getValue(), p, 1.0, 1.0);
                            text = text.replace("<" + entry.getKey() + ">", "");
                        }
                    }
                }
            }
            text = text.replace("  ", " ").replace(" ,", ",");
            changeText(p, text);
        }
    }

    private static void replaceTaggedTableTags(List<XWPFParagraph> paragraphs, TaggedTable taggedTable, Integer row) {
        for (XWPFParagraph p : paragraphs) {
            String text = p.getText();
            for (Map.Entry<String, String> entry : taggedTable.getRows().get(row).getCellsTags().entrySet()) {
                if (text.contains("<" + entry.getKey() + ">")) {
                    if (!entry.getKey().contains(".IMG")) {
                        text = text.replace("<" + entry.getKey() + ">", Optional.ofNullable(entry.getValue()).orElse(""));
                    } else {
                        String parentEntryValue = taggedTable.getRows().get(row).getCellsTags()
                                .get(entry.getKey().replace(".IMG", ""));
                        if (parentEntryValue.equals("TRUE"))
                            insertImage(entry.getValue(), p, 2.3, 2.3);
                        text = text.replace("<" + entry.getKey() + ">", "");
                    }
                }
            }
            text = text.replace("<[" + taggedTable.getTableName() + "]Sequence>", String.valueOf(row + 1));
            text = text.replace("  ", " ").replace(" ,", ",");
            changeText(p, text);
        }
    }

    public static ByteArrayOutputStream fillTagsByDictionary(String templatePath, Map<String, String> simpleTags,
                                                             Map<String, TaggedTable> taggedTables, Map<String, String> htmlTables,
                                     Boolean isPDF) throws Exception {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(templatePath));

        for (Map.Entry<String, String> entry : htmlTables.entrySet()) {
            MyXWPFHtmlDocument myXWPFHtmlDocument = createHtmlDoc(doc, entry.getKey());
            myXWPFHtmlDocument.setHtml(myXWPFHtmlDocument.getHtml().replace("<body></body>",
                    "<body>" + entry.getValue() + "</body>"
            ));
            replaceIBodyElementWithAltChunk(doc, "<" + entry.getKey() + ">", myXWPFHtmlDocument);
        }

        // Замена тэгов щаблона значениями по словарю
        replaceSimpleTags(doc.getParagraphs(), simpleTags);

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
                            replaceSimpleTags(cellObj.getParagraphs(), simpleTags);
                            replaceTaggedTableTags(cellObj.getParagraphs(), taggedTable, row);
                            ifTagsCalculate(cellObj.getParagraphs());
                        }
                        table.addRow(newRow, table.getNumberOfRows() - 1);
                    }
                    table.removeRow(table.getNumberOfRows() - 1);
                }
            }
        }

        // Проверка условий и их разрешение
        ifTagsCalculate(doc.getParagraphs());

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
                    replaceSimpleTags(cells.get(cell).getParagraphs(), simpleTags);
                    ifTagsCalculate(cells.get(cell).getParagraphs());
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
