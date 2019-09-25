package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.DocNumberPrefixes;

import java.util.List;

@Transactional(readOnly = true)
public interface DocNumberPrefixesRepository extends JpaRepository<DocNumberPrefixes, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DocNumberPrefixes d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT d FROM DocNumberPrefixes d WHERE d.id=:id ORDER BY d.name ASC")
    List<DocNumberPrefixes> getAll(@Param("id") int id);
}
