package ru.gbuac.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.PublishData;

@Transactional(readOnly = true)
public interface PublishDataRepository extends JpaRepository<PublishData, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM PublishData p WHERE p.id=:id")
    void delete(@Param("id") int id);

    @Query("SELECT p FROM PublishData p WHERE p.doc.id=:docId")
    PublishData getByDocId(@Param("docId") int docId);
}
