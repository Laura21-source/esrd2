package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.DocAgreement;
import ru.gbuac.to.DocAgreementTo;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DocAgreementRepository extends JpaRepository<DocAgreement, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DocAgreement a WHERE a.id=:id AND a.doc.id=:docId")
    int delete(@Param("id") int id, @Param("docId") int docId);

    @Query("SELECT (CASE WHEN lower(a.user.name)=lower(:userName) THEN TRUE ELSE FALSE END) " +
            "FROM DocAgreement a WHERE a.doc.id=:docId AND a.currentUser = TRUE")
    boolean isTimeForAgreeForUser(@Param("docId") int docId, @Param("userName") String userName);

    /*
    @Query("SELECT max(a) FROM DocAgreement a WHERE a.doc.id=:docId AND a.currentUser = TRUE")
    DocAgreement getCurrentAgreement(@Param("docId") int docId);

    @Query("SELECT max(a) FROM DocAgreement a WHERE a.doc.id=:docId AND a.ordering = (SELECT a.ordering+1 FROM DocAgreement a WHERE a.doc.id=:docId AND a.currentUser = TRUE)")
    DocAgreement getNextAgreement(@Param("docId") int docId);
    */
    @Query("SELECT max(a) FROM DocAgreement a WHERE a.doc.id=:docId AND a.finalUser = TRUE")
    DocAgreement getFinalAgreement(@Param("docId") int docId);

    @Query("SELECT max(a) FROM DocAgreement a WHERE a.doc.id=:docId AND a.decisionType is NOT NULL AND a.finalUser = TRUE")
    Optional<Boolean> isFinalAgreementStage(@Param("docId") int docId);

    @Query("SELECT a FROM DocAgreement a WHERE a.doc.id=:docId AND a.ordering=:ordering")
    DocAgreement getByOrder(@Param("docId") int docId, @Param("ordering") int ordering);

    @Query("SELECT a FROM DocAgreement a WHERE a.doc.id=:docId order by a.ordering")
    List<DocAgreement> getAll(@Param("docId") int docId);

    @Query("SELECT new ru.gbuac.to.DocAgreementTo(a.id, a.ordering, u.id, u.name, u.lastname, u.firstname, u.patronym, u.email, u.position, " +
            "a.agreedDateTime, a.comment, a.decisionType, a.returnedUser.id, a.finalUser, a.currentUser) " +
            "FROM DocAgreement a JOIN a.user u WHERE a.doc.id=:docId ORDER BY a.ordering")
    List<DocAgreementTo> getAgreementList(@Param("docId") int docId);
}
