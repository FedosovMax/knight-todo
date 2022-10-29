package com.knighttodo.todocore.character.gateway.privatedb.mapper;

import com.knighttodo.todocore.character.domain.ItemVO;
import com.knighttodo.todocore.character.exception.UnsupportedTypeException;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Armor;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Item;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Weapon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemMapper {

    private final ArmorMapper armorMapper;
    private final WeaponMapper weaponMapper;

    public ItemVO toItemVO(Item item) {
        if (item instanceof Weapon) {
            return weaponMapper.toWeaponVO((Weapon) item);
        } else if (item instanceof Armor) {
            return armorMapper.toArmorVO((Armor) item);
        }
        throw new UnsupportedTypeException(String.format("Unsupported item type : %s", item.getClass()));
    }
}
