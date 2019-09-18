package ru.gbuac.to;

import lombok.*;
import org.hibernate.validator.constraints.SafeHtml;
import ru.gbuac.model.DecisionType;

import java.time.LocalDateTime;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter

public class DocAgreementTo {

    @SafeHtml
    private
    String lastName;

    @SafeHtml
    private
    String firstName;

    @SafeHtml
    private
    String patronym;

    @SafeHtml
    private
    String position;

    private LocalDateTime agreedDateTime;

    @SafeHtml
    private
    String comment;

    private DecisionType decisionType;

    private boolean currentUser;

    public DocAgreementTo(@SafeHtml String lastName, @SafeHtml String firstName, @SafeHtml String patronym,
                          @SafeHtml String position, LocalDateTime agreedDateTime, @SafeHtml String comment,
                          DecisionType decisionType, boolean currentUser) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronym = patronym;
        this.position = position;
        this.agreedDateTime = agreedDateTime;
        this.comment = comment;
        this.decisionType = decisionType;
        this.currentUser = currentUser;
    }
}
