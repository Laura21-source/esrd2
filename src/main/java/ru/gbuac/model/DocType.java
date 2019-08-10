package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "docfields")
public class DocType extends NamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "docType")
    private List<Doc> docs;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "docType")
    private List<DocTypeFields> docTypeFields;
}
