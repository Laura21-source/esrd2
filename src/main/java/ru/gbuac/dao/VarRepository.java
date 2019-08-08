package ru.gbuac.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gbuac.model.Var;

public interface VarRepository extends JpaRepository<Var, Integer> {
}
