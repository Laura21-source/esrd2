package ru.gbuac.to;

import org.hibernate.validator.constraints.SafeHtml;

public class PdfTo {
    @SafeHtml
    String urlPDF;

    public PdfTo(@SafeHtml String urlPDF) {
        this.urlPDF = urlPDF;
    }

    public String getUrlPDF() {
        return urlPDF;
    }

    public void setUrlPDF(String urlPDF) {
        this.urlPDF = urlPDF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PdfTo pdfTo = (PdfTo) o;

        return urlPDF != null ? urlPDF.equals(pdfTo.urlPDF) : pdfTo.urlPDF == null;
    }

    @Override
    public int hashCode() {
        return urlPDF != null ? urlPDF.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PdfTo{" +
                "urlPDF='" + urlPDF + '\'' +
                '}';
    }
}
