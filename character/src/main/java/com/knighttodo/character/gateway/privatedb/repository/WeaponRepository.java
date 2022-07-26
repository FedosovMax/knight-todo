package com.knighttodo.character.gateway.privatedb.repository;

import com.knighttodo.character.gateway.privatedb.representation.Weapon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeaponRepository extends JpaRepository<Weapon, String> {
}
