package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.CharacterVO;
import com.knighttodo.todocore.character.exception.CharacterNotFoundException;
import com.knighttodo.todocore.character.service.CharacterService;
import com.knighttodo.todocore.character.service.privatedb.mapper.CharacterMapper;
import com.knighttodo.todocore.character.service.privatedb.repository.CharacterRepository;
import com.knighttodo.todocore.character.service.privatedb.representation.Character;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;

    @Override
    public CharacterVO save(CharacterVO characterVO) {
        Character character = characterRepository.save(characterMapper.toCharacter(characterVO));
        return characterMapper.toCharacterVO(character);
    }

    @Override
    public List<CharacterVO> findAll() {
        return characterRepository.findAll().stream()
                .map(characterMapper::toCharacterVO)
                .collect(Collectors.toList());
    }

    @Override
    public CharacterVO findById(String characterId) {
        return characterRepository.findById(characterId).map(characterMapper::toCharacterVO)
            .orElseThrow(() -> new CharacterNotFoundException(
                String.format("Character with such id:%s can't be found", characterId)));
    }

    @Override
    public CharacterVO updateCharacter(CharacterVO changedCharacterVO, String characterId) {
        CharacterVO characterVO = findById(characterId);
        characterVO.setName(changedCharacterVO.getName());
        Character character = characterRepository.save(characterMapper.toCharacter(characterVO));
        return characterMapper.toCharacterVO(character);
    }

    @Override
    public void addExperience(long experience, String userId) {
        CharacterVO characterVO = characterRepository.findById(userId).map(characterMapper::toCharacterVO)
            .orElseThrow(() -> new CharacterNotFoundException(
                String.format("Character with such user id:%s can't be found", userId)));
        characterVO.setExperience(characterVO.getExperience() + experience);
        characterRepository.save(characterMapper.toCharacter(characterVO));
    }

    @Override
    public void deleteById(String characterId) {
        characterRepository.deleteById(characterId);
    }
}
