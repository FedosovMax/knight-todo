package com.knighttodo.character.gateway.privatedb.mapper;

import com.knighttodo.character.domain.WeaponVO;
import com.knighttodo.character.gateway.privatedb.representation.Weapon;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface WeaponMapper {

    Weapon toWeapon(WeaponVO weaponVO);

    WeaponVO toWeaponVO(Weapon weapon);
}
