package ru.gbuac.to;

import org.hibernate.validator.constraints.SafeHtml;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DocTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @SafeHtml
    private String regNum;

    private LocalDateTime regDateTime;

    @SafeHtml
    private String projectRegNum;

    private LocalDateTime projectRegDateTime = LocalDateTime.now();

    private LocalDateTime insertDateTime = LocalDateTime.now();

    private Integer docTypeId;

    private Integer currentAgreementStage;

    private Boolean finalStage;

    @SafeHtml
    private String UrlPDF;

    private List<DocFieldsTo> childFields;

    public DocTo() {
    }

    public DocTo(Integer id, @SafeHtml String regNum, LocalDateTime regDateTime, @SafeHtml String projectRegNum,
                 LocalDateTime projectRegDateTime, LocalDateTime insertDateTime, Integer docTypeId,
                 Integer currentAgreementStage, Boolean finalStage, @SafeHtml String urlPDF, List<DocFieldsTo> childFields) {
        super(id);
        this.regNum = regNum;
        this.regDateTime = regDateTime;
        this.projectRegNum = projectRegNum;
        this.projectRegDateTime = projectRegDateTime;
        this.insertDateTime = insertDateTime;
        this.docTypeId = docTypeId;
        this.currentAgreementStage = currentAgreementStage;
        this.finalStage = finalStage;
        this.UrlPDF = urlPDF;
        this.childFields = childFields;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public LocalDateTime getRegDateTime() {
        return regDateTime;
    }

    public void setRegDateTime(LocalDateTime regDateTime) {
        this.regDateTime = regDateTime;
    }

    public String getProjectRegNum() {
        return projectRegNum;
    }

    public void setProjectRegNum(String projectRegNum) {
        this.projectRegNum = projectRegNum;
    }

    public LocalDateTime getProjectRegDateTime() {
        return projectRegDateTime;
    }

    public void setProjectRegDateTime(LocalDateTime projectRegDateTime) {
        this.projectRegDateTime = projectRegDateTime;
    }

    public LocalDateTime getInsertDateTime() {
        return insertDateTime;
    }

    public void setInsertDateTime(LocalDateTime insertDateTime) {
        this.insertDateTime = insertDateTime;
    }

    public Integer getDocTypeId() {
        return docTypeId;
    }

    public void setDocTypeId(Integer docTypeId) {
        this.docTypeId = docTypeId;
    }

    public Integer getCurrentAgreementStage() {
        return currentAgreementStage;
    }

    public void setCurrentAgreementStage(Integer currentAgreementStage) {
        this.currentAgreementStage = currentAgreementStage;
    }

    public Boolean isFinalStage() {
        return finalStage;
    }

    public void setFinalStage(Boolean finalStage) {
        this.finalStage = finalStage;
    }

    public String getUrlPDF() {
        return UrlPDF;
    }

    public void setUrlPDF(String urlPDF) {
        UrlPDF = urlPDF;
    }

    public List<DocFieldsTo> getChildFields() {
        return childFields;
    }

    public void setChildFields(List<DocFieldsTo> childFields) {
        this.childFields = childFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocTo docTo = (DocTo) o;

        if (regNum != null ? !regNum.equals(docTo.regNum) : docTo.regNum != null) return false;
        if (regDateTime != null ? !regDateTime.equals(docTo.regDateTime) : docTo.regDateTime != null) return false;
        if (projectRegNum != null ? !projectRegNum.equals(docTo.projectRegNum) : docTo.projectRegNum != null)
            return false;
        if (projectRegDateTime != null ? !projectRegDateTime.equals(docTo.projectRegDateTime) : docTo.projectRegDateTime != null)
            return false;
        if (insertDateTime != null ? !insertDateTime.equals(docTo.insertDateTime) : docTo.insertDateTime != null)
            return false;
        if (docTypeId != null ? !docTypeId.equals(docTo.docTypeId) : docTo.docTypeId != null) return false;
        if (currentAgreementStage != null ? !currentAgreementStage.equals(docTo.currentAgreementStage) : docTo.currentAgreementStage != null)
            return false;
        if (finalStage != null ? !finalStage.equals(docTo.finalStage) : docTo.finalStage != null) return false;
        if (UrlPDF != null ? !UrlPDF.equals(docTo.UrlPDF) : docTo.UrlPDF != null) return false;
        return childFields != null ? childFields.equals(docTo.childFields) : docTo.childFields == null;
    }

    @Override
    public int hashCode() {
        int result = regNum != null ? regNum.hashCode() : 0;
        result = 31 * result + (regDateTime != null ? regDateTime.hashCode() : 0);
        result = 31 * result + (projectRegNum != null ? projectRegNum.hashCode() : 0);
        result = 31 * result + (projectRegDateTime != null ? projectRegDateTime.hashCode() : 0);
        result = 31 * result + (insertDateTime != null ? insertDateTime.hashCode() : 0);
        result = 31 * result + (docTypeId != null ? docTypeId.hashCode() : 0);
        result = 31 * result + (currentAgreementStage != null ? currentAgreementStage.hashCode() : 0);
        result = 31 * result + (finalStage != null ? finalStage.hashCode() : 0);
        result = 31 * result + (UrlPDF != null ? UrlPDF.hashCode() : 0);
        result = 31 * result + (childFields != null ? childFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocTo{" +
                "id=" + id +
                ", regNum='" + regNum + '\'' +
                ", regDateTime=" + regDateTime +
                ", projectRegNum='" + projectRegNum + '\'' +
                ", projectRegDateTime=" + projectRegDateTime +
                ", insertDateTime=" + insertDateTime +
                ", docTypeId=" + docTypeId +
                ", currentAgreementStage=" + currentAgreementStage +
                ", finalStage=" + finalStage +
                ", UrlPDF='" + UrlPDF + '\'' +
                ", childFields=" + childFields +
                '}';
    }
}
