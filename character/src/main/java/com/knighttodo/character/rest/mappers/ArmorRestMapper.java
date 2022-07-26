package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.ArmorVO;
import com.knighttodo.character.rest.request.ArmorRequestDto;
import com.knighttodo.character.rest.response.ArmorResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonusRestMapper.class)
public interface ArmorRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    ArmorVO toArmorVO(ArmorRequestDto requestDto);

    ArmorResponseDto toArmorResponseDto(ArmorVO armorVO);
}
