package ru.gbuac.util;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Templater {
    public void InsertValuesInTags(String templatePath, Map<String, String> tagsValues, String outputDocPath)
            throws InvalidFormatException, IOException {
        XWPFDocument doc = new XWPFDocument(OPCPackage.open(templatePath));
        for (XWPFParagraph p : doc.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null) {
                        for (Map.Entry<String, String> entry : tagsValues.entrySet()) {
                            text = text.replace(entry.getKey(), entry.getValue());
                        }
                        r.setText(text, 0);
                    }
                }
            }
        }
    }
}
