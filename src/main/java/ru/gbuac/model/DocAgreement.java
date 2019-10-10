package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doc_agreement")
public class DocAgreement extends BaseEntity {

    @Column(name = "ordering")
    private int ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Doc doc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "returned_user_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User returnedUser;

    @Column(name = "agreed_datetime", nullable = true)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime agreedDateTime;

    @SafeHtml
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "decision_type")
    private DecisionType decisionType;

    @Column(name = "final_user")
    private boolean finalUser;

    @Column(name = "cur_user")
    private boolean currentUser;

}
