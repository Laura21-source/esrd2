package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.DocTypeRoutes;

import java.util.List;

@Transactional(readOnly = true)
public interface DocTypeRoutesRepository extends JpaRepository<DocTypeRoutes, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM DocTypeRoutes d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT d.username FROM DocTypeRoutes d WHERE d.agreeStage=:agreeStage AND d.docType.id=:docTypeId")
    List<String> getGrantedUsersOnStageOfDocType(@Param("agreeStage") int agreeStage, @Param("docTypeId") int docTypeId);

    @Query("SELECT d FROM DocTypeRoutes d WHERE d.username=:userName")
    List<DocTypeRoutes> getByUserName(@Param("userName") String userName);

    @Query("SELECT max(d.agreeStage) FROM DocTypeRoutes d WHERE d.docType.id=:docTypeId")
    int getFinalStageForDocType(@Param("docTypeId") int docTypeId);
}
