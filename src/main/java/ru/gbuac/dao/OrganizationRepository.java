package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gbuac.model.DocValuedFields;
import ru.gbuac.model.Organization;

import java.util.List;

@Transactional(readOnly = true)
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Organization o WHERE o.id=:id")
    void delete(@Param("id") int id);

    @Query("SELECT o FROM Organization o WHERE o.inn=:orgInn")
    Organization getOrgByInn(@Param("orgInn") String orgInn);
}
