package com.knighttodo.todocore.character.gateway.privatedb.repository;

import com.knighttodo.todocore.character.gateway.privatedb.representation.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, String> {
}
