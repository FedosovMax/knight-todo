package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.CharacterVO;
import com.knighttodo.character.rest.request.CharacterRequestDto;
import com.knighttodo.character.rest.response.CharacterResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CharacterRestMapper {

    @Mapping(target = "name", source = "characterName")
    CharacterVO toCharacterVO(CharacterRequestDto requestDto);

    @Mapping(target = "characterName", source = "name")
    CharacterResponseDto toCharacterResponseDto(CharacterVO characterVO);
}
