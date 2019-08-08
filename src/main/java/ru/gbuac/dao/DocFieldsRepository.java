package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.DocFields;

public interface DocFieldsRepository extends JpaRepository<DocFields, Integer> {
}
