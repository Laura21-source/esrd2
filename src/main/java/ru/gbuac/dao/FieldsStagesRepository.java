package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.FieldsStages;

import java.util.List;

@Transactional(readOnly = true)
public interface FieldsStagesRepository extends JpaRepository<FieldsStages, Integer> {
    @Query("SELECT f FROM FieldsStages f WHERE f.docTypeId=:docTypeId")
    List<FieldsStages> getAll(@Param("docTypeId") int docTypeId);
}
