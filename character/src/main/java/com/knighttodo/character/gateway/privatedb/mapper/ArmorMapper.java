package com.knighttodo.character.gateway.privatedb.mapper;

import com.knighttodo.character.domain.ArmorVO;
import com.knighttodo.character.gateway.privatedb.representation.Armor;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface ArmorMapper {

    Armor toArmor(ArmorVO armorVO);

    ArmorVO toArmorVO(Armor armor);
}
