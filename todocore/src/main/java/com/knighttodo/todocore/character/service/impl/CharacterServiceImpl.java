package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.CharacterVO;
import com.knighttodo.todocore.character.exception.CharacterNotFoundException;
import com.knighttodo.todocore.character.gateway.CharacterGateway;
import com.knighttodo.todocore.character.service.CharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CharacterServiceImpl implements CharacterService {

    private final CharacterGateway characterGateway;

    @Override
    public CharacterVO save(CharacterVO characterVO) {
        return characterGateway.save(characterVO);
    }

    @Override
    public List<CharacterVO> findAll() {
        return characterGateway.findAll();
    }

    @Override
    public CharacterVO findById(String characterId) {
        return characterGateway.findById(characterId)
            .orElseThrow(() -> new CharacterNotFoundException(
                String.format("Character with such id:%s can't be found", characterId)));
    }

    @Override
    public CharacterVO updateCharacter(CharacterVO changedCharacterVO, String characterId) {
        CharacterVO characterVO = findById(characterId);
        characterVO.setName(changedCharacterVO.getName());
        return characterGateway.save(characterVO);
    }

    @Override
    public void addExperience(long experience, String userId) {
        CharacterVO characterVO = characterGateway.findByUserId(userId)
            .orElseThrow(() -> new CharacterNotFoundException(
                String.format("Character with such user id:%s can't be found", userId)));
        characterVO.setExperience(characterVO.getExperience() + experience);
        characterGateway.save(characterVO);
    }

    @Override
    public void deleteById(String characterId) {
        characterGateway.deleteById(characterId);
    }
}
