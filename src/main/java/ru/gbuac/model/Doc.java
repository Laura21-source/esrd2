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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doc")
    private List<DocAgreement> agreementList;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Department> executorDepartments;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private List<User> executorUsers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doc", cascade = CascadeType.ALL)
    private List<DocValuedFields> docValuedFields;

    @Column(name = "url_pdf")
    private String urlPDF;

    public Doc(Integer id, String regNum, LocalDateTime regDateTime, @NotNull String projectRegNum,
               @NotNull LocalDateTime projectRegDateTime, @NotNull LocalDateTime insertDateTime,
               @NotNull DocType docType, List<Department> executorDepartments, List<User> executorUsers,
               List<DocValuedFields> docValuedFields, String urlPDF) {
        super(id);
        this.regNum = regNum;
        this.regDateTime = regDateTime;
        this.projectRegNum = projectRegNum;
        this.projectRegDateTime = projectRegDateTime;
        this.insertDateTime = insertDateTime;
        this.docType = docType;
        this.executorDepartments = executorDepartments;
        this.executorUsers = executorUsers;
        this.docValuedFields = docValuedFields;
        this.urlPDF = urlPDF;
    }
}
