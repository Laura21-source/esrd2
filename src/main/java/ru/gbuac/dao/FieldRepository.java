package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.Field;

public interface FieldRepository extends JpaRepository<Field, Integer> {
}
