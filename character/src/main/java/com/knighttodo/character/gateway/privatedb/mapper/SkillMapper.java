package com.knighttodo.character.gateway.privatedb.mapper;

import com.knighttodo.character.domain.SkillVO;
import com.knighttodo.character.gateway.privatedb.representation.Skill;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface SkillMapper {

    Skill toSkill(SkillVO skillVO);

    SkillVO toSkillVO(Skill skill);
}
