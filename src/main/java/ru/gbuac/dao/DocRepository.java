package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.Doc;

import java.util.List;

@Transactional(readOnly = true)
public interface DocRepository extends JpaRepository<Doc, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Doc d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT d FROM Doc d WHERE d.docType.id=:docTypeId AND d.currentAgreementStage=:stage AND d.regNum IS NULL")
    List<Doc> getAllAgreementByDocTypeAndStage(@Param("docTypeId") int docTypeId, @Param("stage") int stage);

    @Query("SELECT d FROM Doc d WHERE d.docType.id=:docTypeId AND d.currentAgreementStage>:stage")
    List<Doc> getAllAgreedByDocTypeAndStage(@Param("docTypeId") int docTypeId, @Param("stage") int stage);

    @Query("SELECT d FROM Doc d WHERE d.docType.id=:docTypeId AND d.currentAgreementStage>:stage AND d.regNum IS NOT NULL")
    List<Doc> getAllRegisteredByDocTypeAndStage(@Param("docTypeId") int docTypeId, @Param("stage") int stage);

    @Query("SELECT d FROM Doc d")
    List<Doc> getAll();

    @Query("SELECT d FROM Doc d WHERE d.regNum IS NULL")
    List<Doc> getAllAgreement();

    @Query("SELECT d FROM Doc d WHERE d.regNum IS NOT NULL")
    List<Doc> getAllRegistered();


    @Transactional
    @Modifying
    @Query("UPDATE Doc d SET d.UrlPDF=:urlPdf WHERE d.id=:id")
    void setUrlPDF(@Param("id") int id, @Param("urlPdf") String urlPdf);
}
