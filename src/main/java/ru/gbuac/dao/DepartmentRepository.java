package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.gbuac.model.Department;

import java.util.List;

@Transactional(readOnly = true)
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Department d WHERE d.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT d FROM Department d WHERE d.topLevel=TRUE")
    List<Department> getAllTopLevelDepartment();
}
