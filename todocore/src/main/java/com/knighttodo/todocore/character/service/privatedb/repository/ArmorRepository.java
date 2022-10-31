package com.knighttodo.todocore.character.service.privatedb.repository;

import com.knighttodo.todocore.character.service.privatedb.representation.Armor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorRepository extends JpaRepository<Armor, String> {
}
