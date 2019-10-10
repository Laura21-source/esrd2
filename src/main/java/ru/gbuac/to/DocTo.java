package ru.gbuac.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import ru.gbuac.model.DocAgreement;
import ru.gbuac.model.DocStatus;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DocTo extends BaseTo {
    @SafeHtml
    private String regNum;

    private LocalDateTime regDateTime;

    @SafeHtml
    private String projectRegNum;

    private LocalDateTime projectRegDateTime = LocalDateTime.now();

    private LocalDateTime insertDateTime = LocalDateTime.now();

    private Integer docTypeId;

    private DocStatus docStatus;

    private Boolean finalStage;

    private Boolean canAgree;

    @SafeHtml
    private String UrlPDF;

    @SafeHtml
    private String comment;

    private List<DocAgreementTo> agreementList;

    private List<DocFieldsTo> childFields;

    public DocTo(Integer id, @SafeHtml String regNum, LocalDateTime regDateTime, @SafeHtml String projectRegNum,
                 LocalDateTime projectRegDateTime, LocalDateTime insertDateTime, Integer docTypeId, DocStatus docStatus,
                 Boolean finalStage, Boolean canAgree, @SafeHtml String urlPDF,
                 @SafeHtml String comment, List<DocAgreementTo> agreementList, List<DocFieldsTo> childFields) {
        super(id);
        this.regNum = regNum;
        this.regDateTime = regDateTime;
        this.projectRegNum = projectRegNum;
        this.projectRegDateTime = projectRegDateTime;
        this.insertDateTime = insertDateTime;
        this.docTypeId = docTypeId;
        this.docStatus = docStatus;
        this.finalStage = finalStage;
        this.canAgree = canAgree;
        this.UrlPDF = urlPDF;
        this.comment = comment;
        this.agreementList = agreementList;
        this.childFields = childFields;
    }
}
