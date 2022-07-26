package com.knighttodo.character.gateway.privatedb.mapper;

import com.knighttodo.character.domain.BonusVO;
import com.knighttodo.character.gateway.privatedb.representation.Bonus;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BonusMapper {

    Bonus toBonus(BonusVO bonusVO);

    BonusVO toBonusVO(Bonus bonus);
}
