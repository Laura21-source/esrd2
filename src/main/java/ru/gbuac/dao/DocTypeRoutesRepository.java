package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.DocTypeRoutes;
import ru.gbuac.model.User;

import java.util.List;

@Transactional(readOnly = true)
public interface DocTypeRoutesRepository extends JpaRepository<DocTypeRoutes, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DocTypeRoutes d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM DocTypeRoutes d WHERE d.agreeStage=:agreeStage AND d.docType.id=:docTypeId AND d.user.name=:userName")
    boolean isHasRightsForDocTypeOnStage(@Param("agreeStage") int agreeStage, @Param("docTypeId") int docTypeId,
                                         @Param("userName") String userName);

    @Query("SELECT d FROM DocTypeRoutes d WHERE d.user.name=:userName")
    List<DocTypeRoutes> getByUserName(@Param("userName") String userName);

    @Query("SELECT max(d.agreeStage) FROM DocTypeRoutes d WHERE d.docType.id=:docTypeId")
    int getFinalStageForDocType(@Param("docTypeId") int docTypeId);
}
