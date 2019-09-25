package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.DocAgreement;
import ru.gbuac.to.DocAgreementTo;

import java.util.List;

@Transactional(readOnly = true)
public interface DocAgreementRepository extends JpaRepository<DocAgreement, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DocAgreement a WHERE a.id=:id AND a.doc.id=:docId")
    int delete(@Param("id") int id, @Param("docId") int docId);

    @Query("SELECT new ru.gbuac.to.DocAgreementTo(u.name, u.lastname, u.firstname, u.patronym, u.position, l.agreedDateTime, " +
            "l.comment, l.decisionType, CASE WHEN r.agreeStage = d.currentAgreementStage THEN TRUE ELSE FALSE END) " +
            "FROM DocTypeRoutes r LEFT OUTER JOIN r.user u LEFT OUTER JOIN r.docType.doc d " +
            "LEFT OUTER JOIN d.docAgreements l ON l.user.id = r.user.id WHERE d.id=:docId")
    List<DocAgreementTo> getAgreementList(@Param("docId") int docId);
}
