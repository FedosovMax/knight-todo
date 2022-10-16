package com.knighttodo.todocore.character.rest.mappers;

import com.knighttodo.todocore.character.domain.CharacterVO;
import com.knighttodo.todocore.character.rest.request.CharacterRequestDto;
import com.knighttodo.todocore.character.rest.response.CharacterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterRestMapper {

    @Mapping(target = "name", source = "characterName")
    CharacterVO toCharacterVO(CharacterRequestDto requestDto);

    @Mapping(target = "characterName", source = "name")
    CharacterResponseDto toCharacterResponseDto(CharacterVO characterVO);
}
