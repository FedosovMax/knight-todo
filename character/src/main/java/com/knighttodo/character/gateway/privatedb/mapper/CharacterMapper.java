package com.knighttodo.character.gateway.privatedb.mapper;

import com.knighttodo.character.domain.CharacterVO;
import com.knighttodo.character.gateway.privatedb.representation.Character;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CharacterMapper {

    Character toCharacter(CharacterVO characterVO);

    CharacterVO toCharacterVO(Character character);
}
