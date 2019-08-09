package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.Var;
import ru.gbuac.model.VarElem;

public interface VarElemRepository extends JpaRepository<VarElem, Integer> {
}
