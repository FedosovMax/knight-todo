package com.knighttodo.todocore.character.service.privatedb.mapper;

import com.knighttodo.todocore.character.domain.SkillVO;
import com.knighttodo.todocore.character.service.privatedb.representation.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = BonusMapper.class)
public interface SkillMapper {

    Skill toSkill(SkillVO skillVO);

    SkillVO toSkillVO(Skill skill);
}
