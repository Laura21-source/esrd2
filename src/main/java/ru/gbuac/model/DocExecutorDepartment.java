package ru.gbuac.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doc_executor_departments")
public class DocExecutorDepartment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Doc doc;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_department_id", nullable = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department executorDepartment;

    public DocExecutorDepartment(Integer id, Doc doc, Department executorDepartment) {
        super(id);
        this.doc = doc;
        this.executorDepartment = executorDepartment;
    }
}
