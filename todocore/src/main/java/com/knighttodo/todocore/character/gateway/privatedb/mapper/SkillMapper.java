package com.knighttodo.todocore.character.gateway.privatedb.mapper;

import com.knighttodo.todocore.character.domain.SkillVO;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface SkillMapper {

    Skill toSkill(SkillVO skillVO);

    SkillVO toSkillVO(Skill skill);
}
