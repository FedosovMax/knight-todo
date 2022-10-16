package com.knighttodo.todocore.character.rest.mappers;

import com.knighttodo.todocore.character.domain.ArmorVO;
import com.knighttodo.todocore.character.domain.ItemVO;
import com.knighttodo.todocore.character.domain.WeaponVO;
import com.knighttodo.todocore.character.exception.UnsupportedTypeException;
import com.knighttodo.todocore.character.rest.response.ItemResponseDto;
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
