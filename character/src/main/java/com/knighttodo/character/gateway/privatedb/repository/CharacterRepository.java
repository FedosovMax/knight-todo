package com.knighttodo.character.gateway.privatedb.repository;

import com.knighttodo.character.gateway.privatedb.representation.Character;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

    Optional<Character> findByUserId(String userId);
}
