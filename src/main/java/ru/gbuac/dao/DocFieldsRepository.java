package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.DocValuedFields;

public interface DocFieldsRepository extends JpaRepository<DocValuedFields, Integer> {
}
