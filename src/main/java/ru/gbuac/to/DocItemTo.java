package ru.gbuac.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.SafeHtml;
import ru.gbuac.model.Department;
import ru.gbuac.model.DocStatus;
import ru.gbuac.model.User;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DocItemTo extends BaseTo {
    private DocStatus docStatus;

    @SafeHtml
    private String regNum;

    private LocalDateTime regDateTime;

    @SafeHtml
    private String projectRegNum;

    private LocalDateTime projectRegDateTime;

    @SafeHtml
    private String currentAgreeFullName;

    private List<Department> executorDepartments;

    private List<User> executorUsers;

    @SafeHtml
    private String docType;

    public DocItemTo(Integer id, DocStatus docStatus, @SafeHtml String regNum, LocalDateTime regDateTime, @SafeHtml
            String projectRegNum, LocalDateTime projectRegDateTime, @SafeHtml String currentAgreeFullName,
                     @SafeHtml String docType) {
        super(id);
        this.docStatus = docStatus;
        this.regNum = regNum;
        this.regDateTime = regDateTime;
        this.projectRegNum = projectRegNum;
        this.projectRegDateTime = projectRegDateTime;
        this.currentAgreeFullName = currentAgreeFullName;
        this.docType = docType;
    }

    public DocItemTo(Integer id, DocStatus docStatus, @SafeHtml String regNum, LocalDateTime regDateTime,
                     @SafeHtml String projectRegNum, LocalDateTime projectRegDateTime, List<Department>
                             executorDepartments, List<User> executorUsers, @SafeHtml String docType) {
        super(id);
        this.docStatus = docStatus;
        this.regNum = regNum;
        this.regDateTime = regDateTime;
        this.projectRegNum = projectRegNum;
        this.projectRegDateTime = projectRegDateTime;
        this.executorDepartments = executorDepartments;
        this.executorUsers = executorUsers;
        this.docType = docType;
    }
}
