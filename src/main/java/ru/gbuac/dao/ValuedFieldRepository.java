package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.ValuedField;

public interface ValuedFieldRepository extends JpaRepository<ValuedField, Integer> {
}
