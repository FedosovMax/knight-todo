package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.SkillVO;
import com.knighttodo.character.rest.request.SkillRequestDto;
import com.knighttodo.character.rest.response.SkillResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BonusRestMapper.class)
public interface SkillRestMapper {

    @Mapping(target = "bonuses", source = "bonusIds")
    SkillVO toSkillVO(SkillRequestDto requestDto);

    SkillResponseDto toSkillResponseDto(SkillVO skillVO);
}
