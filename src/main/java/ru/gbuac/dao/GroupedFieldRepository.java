package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.GroupedField;

public interface GroupedFieldRepository extends JpaRepository<GroupedField, Integer> {
}
