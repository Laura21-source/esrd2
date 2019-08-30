package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "doctype")
public class DocType extends NamedEntity {
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "docType")
    //private List<Doc> docs;
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "docType")
    //private List<DocTypeFields> docTypeFields;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
