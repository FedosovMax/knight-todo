package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.ArmorVO;
import com.knighttodo.character.domain.ItemVO;
import com.knighttodo.character.domain.WeaponVO;
import com.knighttodo.character.exception.UnsupportedTypeException;
import com.knighttodo.character.rest.response.ItemResponseDto;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemRestMapper {

    private final ArmorRestMapper armorRestMapper;
    private final WeaponRestMapper weaponRestMapper;

    public ItemResponseDto toItemVO(ItemVO itemVO) {
        if (itemVO instanceof WeaponVO) {
            return weaponRestMapper.toWeaponResponseDto((WeaponVO) itemVO);
        } else if (itemVO instanceof ArmorVO) {
            return armorRestMapper.toArmorResponseDto((ArmorVO) itemVO);
        }
        throw new UnsupportedTypeException(String.format("Unsupported itemVO type : %s", itemVO.getClass()));
    }
}
