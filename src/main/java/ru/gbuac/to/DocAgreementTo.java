package ru.gbuac.to;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import ru.gbuac.model.DecisionType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class DocAgreementTo {

    @SafeHtml
    String lastName;

    @SafeHtml
    String firstName;

    @SafeHtml
    String patronym;

    @SafeHtml
    String position;

    LocalDateTime agreedDateTime;

    @SafeHtml
    String comment;

    DecisionType decisionType;

    boolean currentUser;

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
