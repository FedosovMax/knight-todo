package com.knighttodo.character.service.impl;

import com.knighttodo.character.domain.CharacterVO;
import com.knighttodo.character.exception.CharacterNotFoundException;
import com.knighttodo.character.gateway.CharacterGateway;
import com.knighttodo.character.service.CharacterService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
