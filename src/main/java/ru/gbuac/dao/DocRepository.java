package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.Doc;
import ru.gbuac.model.DocStatus;
import ru.gbuac.to.DocNumberTo;

import java.util.List;

@Transactional(readOnly = true)
public interface DocRepository extends JpaRepository<Doc, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Doc d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT DISTINCT d FROM Doc d LEFT JOIN d.resolutions r LEFT JOIN r.resolutionsUsers ru " +
            "WHERE ru.user.name=:userName AND d.docStatus='IN_WORK' ORDER BY d.id")
    List<Doc> getAllInWorkByUserName(@Param("userName") String userName);

    @Query("SELECT new ru.gbuac.to.DocItemTo(d.id, d.docStatus, d.regNum, d.regDateTime, d.projectRegNum, d.projectRegDateTime, " +
            " CONCAT(a.user.lastname, ' ', a.user.firstname, ' ', a.user.patronym), " +
            "d.docType.name) FROM Doc d JOIN d.agreementList a JOIN a.user WHERE " +
            "lower(a.user.name)=lower(:userName) AND a.currentUser=TRUE AND d.docStatus='IN_AGREEMENT' ORDER BY d.id")
    List<Doc> getAllAgreementByUserName(@Param("userName") String userName);

    @Query("SELECT new ru.gbuac.to.DocItemTo(d.id, d.docStatus, d.regNum, d.regDateTime, d.projectRegNum, d.projectRegDateTime, " +
            "(SELECT CONCAT(a.user.lastname, ' ', a.user.firstname, ' ', a.user.patronym) FROM d.agreementList a " +
            "WHERE a.currentUser=TRUE), d.docType.name) FROM Doc d JOIN d.agreementList c WHERE " +
            "(lower(c.user.name)=lower(:userName) AND c.decisionType IS NOT NULL) OR lower(d.initialUser.name)=lower(:userName) ORDER BY d.id")
    List<Doc> getAllAgreedByUserName(@Param("userName") String userName);

    @Query("SELECT new ru.gbuac.to.DocItemTo(d.id, d.docStatus, d.regNum, d.regDateTime, d.projectRegNum, d.projectRegDateTime, " +
            "(SELECT CONCAT(a.user.lastname, ' ', a.user.firstname, ' ', a.user.patronym) FROM d.agreementList a " +
            "WHERE a.currentUser=TRUE), d.docType.name) FROM Doc d JOIN d.agreementList c WHERE " +
            "((lower(c.user.name)=lower(:userName) AND c.decisionType IS NOT NULL) OR lower(d.initialUser.name)=lower(:userName)) " +
            "AND d.docStatus<>'IN_AGREEMENT' ORDER BY d.id")
    List<Doc> getAllRegisteredByUserName(@Param("userName") String userName);

    @Query("SELECT DISTINCT d FROM Doc d LEFT JOIN d.resolutions r LEFT JOIN r.department dep " +
            "LEFT JOIN r.resolutionsUsers ru ORDER BY d.id")
    List<Doc> getAll();

    @Query("SELECT d FROM Doc d WHERE d.docType.id=:docTypeId")
    List<Doc> getAllByDocType(@Param("docTypeId") int docTypeId);

    @Query("SELECT new ru.gbuac.to.DocItemTo(d.id, d.docStatus, d.regNum, d.regDateTime, d.projectRegNum, d.projectRegDateTime, " +
            "(SELECT CONCAT(a.user.lastname, ' ', a.user.firstname, ' ', a.user.patronym) FROM d.agreementList a " +
            "WHERE a.currentUser=TRUE), d.docType.name) FROM Doc d WHERE d.docStatus='IN_AGREEMENT' ORDER BY d.id")
    List<Doc> getAllAgreement();

    @Query("SELECT new ru.gbuac.to.DocItemTo(d.id, d.docStatus, d.regNum, d.regDateTime, d.projectRegNum, d.projectRegDateTime, " +
            "(SELECT CONCAT(a.user.lastname, ' ', a.user.firstname, ' ', a.user.patronym) FROM d.agreementList a " +
            "WHERE a.currentUser=TRUE), d.docType.name) FROM Doc d WHERE d.docStatus<>'IN_AGREEMENT' ORDER BY d.id")
    List<Doc> getAllRegistered();

    @Query("SELECT DISTINCT d FROM Doc d LEFT JOIN d.resolutions r LEFT JOIN r.department dep LEFT JOIN r.resolutionsUsers ru " +
            "WHERE dep.id=:departmentId AND ru.id IS NULL AND d.docStatus='IN_WORK' ORDER BY d.id")
    List<Doc> getAllDistribution(int departmentId);

    @Query("SELECT DISTINCT d FROM Doc d LEFT JOIN d.resolutions r LEFT JOIN r.department dep LEFT JOIN r.resolutionsUsers ru " +
            "WHERE dep.id=:departmentId AND ru.id IS NOT NULL AND d.docStatus='IN_WORK' ORDER BY d.id")
    List<Doc> getAllDistributed(int departmentId);

    @Query("SELECT new ru.gbuac.to.DocNumberTo(d.id, d.regNum) FROM Doc d WHERE d.regNum IS NOT NULL ORDER BY d.regNum")
    List<DocNumberTo> getRegNumbers();

    @Query("SELECT d.docType.id FROM Doc d WHERE d.id=:id")
    int getDocTypeByDocId(@Param("id") int id);

    @Query("SELECT d.docStatus FROM Doc d WHERE d.id=:id")
    DocStatus getDocStatusByDocId(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Doc d SET d.urlPDF=:urlPdf WHERE d.id=:id")
    void setUrlPDF(@Param("id") int id, @Param("urlPdf") String urlPdf);
}
