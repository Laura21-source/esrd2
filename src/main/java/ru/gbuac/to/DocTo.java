package ru.gbuac.to;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DocTo extends BaseTo {
    private String regNum;

    private LocalDate regDate = LocalDate.now();

    private LocalDateTime insertDateTime = LocalDateTime.now();

    private Integer docTypeId;

    private List<DocFieldsTo> docValuedFields;

    public DocTo() {
    }

    public DocTo(Integer id, String regNum, LocalDate regDate, LocalDateTime insertDateTime, Integer docTypeId, List<DocFieldsTo> docValuedFields) {
        super(id);
        this.regNum = regNum;
        this.regDate = regDate;
        this.insertDateTime = insertDateTime;
        this.docTypeId = docTypeId;
        this.docValuedFields = docValuedFields;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
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

    public List<DocFieldsTo> getDocValuedFields() {
        return docValuedFields;
    }

    public void setDocValuedFields(List<DocFieldsTo> docValuedFields) {
        this.docValuedFields = docValuedFields;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocTo docTo = (DocTo) o;

        if (regNum != null ? !regNum.equals(docTo.regNum) : docTo.regNum != null) return false;
        if (regDate != null ? !regDate.equals(docTo.regDate) : docTo.regDate != null) return false;
        if (insertDateTime != null ? !insertDateTime.equals(docTo.insertDateTime) : docTo.insertDateTime != null)
            return false;
        if (docTypeId != null ? !docTypeId.equals(docTo.docTypeId) : docTo.docTypeId != null) return false;
        return docValuedFields != null ? docValuedFields.equals(docTo.docValuedFields) : docTo.docValuedFields == null;
    }

    @Override
    public int hashCode() {
        int result = regNum != null ? regNum.hashCode() : 0;
        result = 31 * result + (regDate != null ? regDate.hashCode() : 0);
        result = 31 * result + (insertDateTime != null ? insertDateTime.hashCode() : 0);
        result = 31 * result + (docTypeId != null ? docTypeId.hashCode() : 0);
        result = 31 * result + (docValuedFields != null ? docValuedFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocTo{" +
                "id=" + id +
                ", regNum='" + regNum + '\'' +
                ", regDate=" + regDate +
                ", insertDateTime=" + insertDateTime +
                ", docTypeId=" + docTypeId +
                ", docValuedFields=" + docValuedFields +
                '}';
    }
}
