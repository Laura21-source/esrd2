package ru.gbuac.to;

import org.hibernate.validator.constraints.SafeHtml;
import ru.gbuac.model.DecisionType;

import java.time.LocalDateTime;

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

    public DocAgreementTo() {
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalDateTime getAgreedDateTime() {
        return agreedDateTime;
    }

    public void setAgreedDateTime(LocalDateTime agreedDateTime) {
        this.agreedDateTime = agreedDateTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public DecisionType getDecisionType() {
        return decisionType;
    }

    public void setDecisionType(DecisionType decisionType) {
        this.decisionType = decisionType;
    }

    public boolean isCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(boolean currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocAgreementTo that = (DocAgreementTo) o;

        if (currentUser != that.currentUser) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (patronym != null ? !patronym.equals(that.patronym) : that.patronym != null) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (agreedDateTime != null ? !agreedDateTime.equals(that.agreedDateTime) : that.agreedDateTime != null)
            return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;
        return decisionType == that.decisionType;
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (patronym != null ? patronym.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (agreedDateTime != null ? agreedDateTime.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (decisionType != null ? decisionType.hashCode() : 0);
        result = 31 * result + (currentUser ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DocAgreementTo{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronym='" + patronym + '\'' +
                ", position='" + position + '\'' +
                ", agreedDateTime=" + agreedDateTime +
                ", comment='" + comment + '\'' +
                ", decisionType=" + decisionType +
                ", currentUser=" + currentUser +
                '}';
    }
}
