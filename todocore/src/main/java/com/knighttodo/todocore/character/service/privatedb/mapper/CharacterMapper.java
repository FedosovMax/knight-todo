package com.knighttodo.todocore.character.service.privatedb.mapper;

import com.knighttodo.todocore.character.domain.CharacterVO;
import com.knighttodo.todocore.character.service.privatedb.representation.Character;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    Character toCharacter(CharacterVO characterVO);

    CharacterVO toCharacterVO(Character character);
}
