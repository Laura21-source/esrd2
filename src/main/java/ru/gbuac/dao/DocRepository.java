package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.Doc;

public interface DocRepository extends JpaRepository<Doc, Integer> {
}
