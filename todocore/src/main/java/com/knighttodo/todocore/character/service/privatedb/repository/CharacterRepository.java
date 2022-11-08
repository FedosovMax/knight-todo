package com.knighttodo.todocore.character.service.privatedb.repository;

import com.knighttodo.todocore.character.service.privatedb.representation.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, String> {

    Optional<Character> findByUserId(String userId);
}
