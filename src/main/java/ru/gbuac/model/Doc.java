package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doc")
public class Doc extends BaseEntity {
    @NotNull
    @Column(name = "reg_num")
    private String regNum;

    @NotNull
    @Column(name = "reg_date")
    private LocalDate regDate;

    @NotNull
    @Column(name = "insert_datetime", nullable = false, columnDefinition = "timestamp default now()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime insertDateTime = LocalDateTime.now();

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctype_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DocType docType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "doc", cascade = CascadeType.ALL)
    private List<DocValuedFields> docValuedFields;

    public Doc(Integer id, @NotNull String regNum, @NotNull LocalDate regDate, @NotNull LocalDateTime insertDateTime, @NotNull DocType docType, List<DocValuedFields> docValuedFields) {
        super(id);
        this.regNum = regNum;
        this.regDate = regDate;
        this.insertDateTime = insertDateTime;
        this.docType = docType;
        this.docValuedFields = docValuedFields;
    }
}
