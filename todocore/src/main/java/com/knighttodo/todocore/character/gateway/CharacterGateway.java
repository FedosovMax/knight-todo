package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.CharacterVO;

import java.util.List;
import java.util.Optional;

public interface CharacterGateway {

    CharacterVO save(CharacterVO characterVO);

    List<CharacterVO> findAll();

    Optional<CharacterVO> findById(String characterId);

    Optional<CharacterVO> findByUserId(String userId);

    void deleteById(String characterId);
}
