package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.CatalogElem;
import ru.gbuac.model.DocTypeFields;

import java.util.List;

@Transactional(readOnly = true)
public interface CatalogElemRepository extends JpaRepository<CatalogElem, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM CatalogElem c WHERE c.id=:id AND c.catalog.id=:catalogId")
    int delete(@Param("id") int id, @Param("catalogId") int questId);

    @Query("SELECT c FROM CatalogElem c WHERE c.catalog.id=:catalogId ORDER BY c.id ASC")
    List<CatalogElem> getAll(@Param("catalogId") int questId);
}
