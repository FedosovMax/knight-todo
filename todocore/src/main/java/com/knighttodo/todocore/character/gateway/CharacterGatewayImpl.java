package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.CharacterVO;
import com.knighttodo.todocore.character.gateway.privatedb.mapper.CharacterMapper;
import com.knighttodo.todocore.character.gateway.privatedb.repository.CharacterRepository;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CharacterGatewayImpl implements CharacterGateway {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public CharacterVO save(CharacterVO characterVO) {
        Character character = characterMapper.toCharacter(characterVO);
        character.setUserId("user_id");
        Character savedCharacter = characterRepository.save(character);
        return characterMapper.toCharacterVO(savedCharacter);
    }

    @Override
    public List<CharacterVO> findAll() {
        return characterRepository.findAll().stream().map(characterMapper::toCharacterVO).collect(Collectors.toList());
    }

    @Override
    public Optional<CharacterVO> findById(String characterId) {
        return characterRepository.findById(characterId).map(characterMapper::toCharacterVO);
    }

    @Override
    public Optional<CharacterVO> findByUserId(String userId) {
        return characterRepository.findByUserId(userId).map(characterMapper::toCharacterVO);
    }

    @Override
    public void deleteById(String characterId) {
        characterRepository.deleteById(characterId);
    }
}
