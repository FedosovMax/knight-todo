package com.knighttodo.todocore.character.service.privatedb.repository;

import com.knighttodo.todocore.character.service.privatedb.representation.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, String> {
}
