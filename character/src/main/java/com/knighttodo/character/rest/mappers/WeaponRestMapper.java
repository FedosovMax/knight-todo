package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.WeaponVO;
import com.knighttodo.character.rest.request.WeaponRequestDto;
import com.knighttodo.character.rest.response.WeaponResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonusRestMapper.class)
public interface WeaponRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    WeaponVO toWeaponVO(WeaponRequestDto requestDto);

    WeaponResponseDto toWeaponResponseDto(WeaponVO weaponVO);
}
