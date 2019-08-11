package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doc")
public class Doc extends NamedEntity {
    @NotNull
    @Column(name = "reg_num")
    String regNum;

    @NotNull
    @Column(name = "reg_date")
    private LocalDateTime regDate;

    @NotNull
    @Column(name = "insert_date", nullable = false, columnDefinition = "timestamp default now()")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime insertDate = LocalDateTime.now();

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctype_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    DocType docType;
}
