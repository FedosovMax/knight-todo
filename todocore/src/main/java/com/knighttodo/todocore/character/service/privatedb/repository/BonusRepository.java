package com.knighttodo.todocore.character.service.privatedb.repository;

import com.knighttodo.todocore.character.service.privatedb.representation.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BonusRepository extends JpaRepository<Bonus, String> {
}
