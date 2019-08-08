package ru.gbuac.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class GroupedField extends NamedEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "groupedField")
    List<DocTypeFields> docTypeFields;
}
