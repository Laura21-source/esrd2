package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doc")
public class Doc extends BaseEntity {
    @Column(name = "reg_num")
    private String regNum;

    @Column(name = "reg_datetime")
    private LocalDateTime regDateTime;

    @NotNull
    @Column(name = "project_reg_num")
    private String projectRegNum;

    @NotNull
    @Column(name = "project_reg_datetime", nullable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime projectRegDateTime = LocalDateTime.now();;

    @NotNull
    @Column(name = "insert_datetime", nullable = false, columnDefinition = "timestamp default now()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime insertDateTime = LocalDateTime.now();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "docstatus")
    private DocStatus docStatus = DocStatus.IN_AGREEMENT;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctype_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DocType docType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doc", cascade = CascadeType.ALL)
    private List<DocValuedFields> docValuedFields;

    @NotNull
    @Column(name = "cur_agree_stage")
    private Integer currentAgreementStage = 0;

    @Column(name = "url_pdf")
    private String urlPDF;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doc")
    private List<DocAgreement> docAgreements;

    public Doc(Integer id, String regNum, LocalDateTime regDateTime, @NotNull String projectRegNum,
               @NotNull LocalDateTime projectRegDateTime, @NotNull LocalDateTime insertDateTime,
               @NotNull DocType docType, List<DocValuedFields> docValuedFields, Integer currentAgreementStage,
               String urlPDF) {
        super(id);
        this.regNum = regNum;
        this.regDateTime = regDateTime;
        this.projectRegNum = projectRegNum;
        this.projectRegDateTime = projectRegDateTime;
        this.insertDateTime = insertDateTime;
        this.docType = docType;
        this.docValuedFields = docValuedFields;
        this.currentAgreementStage = currentAgreementStage;
        this.urlPDF = urlPDF;
    }
}
