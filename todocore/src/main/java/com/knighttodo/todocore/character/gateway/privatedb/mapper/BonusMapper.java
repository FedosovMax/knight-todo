package com.knighttodo.todocore.character.gateway.privatedb.mapper;

import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Bonus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BonusMapper {

    Bonus toBonus(BonusVO bonusVO);

    BonusVO toBonusVO(Bonus bonus);
}
