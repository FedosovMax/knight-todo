package com.knighttodo.todocore.character.gateway.privatedb.repository;

import com.knighttodo.todocore.character.gateway.privatedb.representation.Armor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArmorRepository extends JpaRepository<Armor, String> {
}
