package com.knighttodo.todocore.character.service.privatedb.mapper;

import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.service.privatedb.representation.Bonus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BonusMapper {

    Bonus toBonus(BonusVO bonusVO);

    BonusVO toBonusVO(Bonus bonus);
}
