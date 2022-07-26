package com.knighttodo.character.gateway.privatedb.repository;

import com.knighttodo.character.gateway.privatedb.representation.Armor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ArmorRepository extends JpaRepository<Armor, String> {
}
