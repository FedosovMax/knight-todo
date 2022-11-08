package com.knighttodo.todocore.character.service.privatedb.mapper;

import com.knighttodo.todocore.character.domain.WeaponVO;
import com.knighttodo.todocore.character.service.privatedb.representation.Weapon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface WeaponMapper {

    Weapon toWeapon(WeaponVO weaponVO);

    WeaponVO toWeaponVO(Weapon weapon);
}
