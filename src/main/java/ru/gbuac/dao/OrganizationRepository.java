package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.Organization;

import java.util.List;

@Transactional(readOnly = true)
public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Organization o WHERE o.id=:id")
    void delete(@Param("id") int id);

    @Query("SELECT o FROM Organization o WHERE o.id=:id  ORDER BY o.shortName ASC")
    List<Organization> getAllOrganizations(@Param("id") int id);

    @Query("SELECT o FROM Organization  o WHERE o.id=:id")
    Organization get(@Param("id") int id);
}
