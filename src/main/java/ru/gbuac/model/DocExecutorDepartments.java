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
public class DocExecutorDepartments extends BaseEntity {

    @Column(name = "ordering")
    Integer ordering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Doc doc;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "executor_department_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Department executorDepartment;

    public DocExecutorDepartments(Integer id, Integer ordering, Doc doc, Department executorDepartment) {
        super(id);
        this.ordering = ordering;
        this.doc = doc;
        this.executorDepartment = executorDepartment;
    }
}
