package com.knighttodo.todocore.character.service.privatedb.mapper;

import com.knighttodo.todocore.character.domain.ArmorVO;
import com.knighttodo.todocore.character.service.privatedb.representation.Armor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface ArmorMapper {

    Armor toArmor(ArmorVO armorVO);

    ArmorVO toArmorVO(Armor armor);
}
