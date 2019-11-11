package ru.gbuac.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface HtmlTableRepository {
    @Transactional
    @Modifying
    @Query("DELETE FROM HtmlTable u WHERE u.id=:id")
    int delete(@Param("id") int id);
}
