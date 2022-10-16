package com.knighttodo.todocore.character.rest.mappers;

import com.knighttodo.todocore.character.domain.WeaponVO;
import com.knighttodo.todocore.character.rest.request.WeaponRequestDto;
import com.knighttodo.todocore.character.rest.response.WeaponResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonusRestMapper.class)
public interface WeaponRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    WeaponVO toWeaponVO(WeaponRequestDto requestDto);

    WeaponResponseDto toWeaponResponseDto(WeaponVO weaponVO);
}
