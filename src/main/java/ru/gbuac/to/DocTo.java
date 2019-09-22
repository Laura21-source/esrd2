package ru.gbuac.to;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import java.time.LocalDateTime;
import java.util.List;

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

    private Integer currentAgreementStage;

    private Boolean finalStage;

    @SafeHtml
    private String UrlPDF;

    private List<DocFieldsTo> childFields;

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
}
