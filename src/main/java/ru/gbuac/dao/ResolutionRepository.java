package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.Resolution;

import java.time.LocalDate;

@Transactional(readOnly = true)
public interface ResolutionRepository extends JpaRepository<Resolution, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Resolution r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Resolution r SET r.controlDate=:controlDate WHERE r.primaryResolution=TRUE AND r.doc.id=:docId")
    void setControlDateForPrimaryResolution(@Param("docId") int docId, @Param("controlDate") LocalDate controlDate);
}
