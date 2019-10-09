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

    @Query("SELECT min(a) FROM DocAgreement a WHERE a.user.name=:userName AND a.doc.id=:docId AND a.agreedDateTime IS NULL")
    DocAgreement getLastForAgreeByUserName(@Param("docId") int docId, @Param("userName") String userName);

    @Query("SELECT min(a) FROM DocAgreement a WHERE a.doc.id=:docId AND a.agreedDateTime IS NULL")
    DocAgreement getLastForAgreeByUserName(@Param("docId") int docId);

    @Query("SELECT new ru.gbuac.to.DocAgreementTo(u.name, u.lastname, u.firstname, u.patronym, u.position, a.agreedDateTime, " +
            "a.comment, a.decisionType, CASE WHEN (a.user.name=:userName AND a.agreedDateTime IS NULL) THEN TRUE ELSE FALSE END) " +
            "FROM DocAgreement a JOIN a.user u WHERE a.doc.id=:docId ORDER BY a.id")
    List<DocAgreementTo> getAgreementList(@Param("docId") int docId, @Param("userName") String userName);
}
