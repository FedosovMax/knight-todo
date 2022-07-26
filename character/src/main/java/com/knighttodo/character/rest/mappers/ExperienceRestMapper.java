package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.ExperienceVO;
import com.knighttodo.character.rest.request.ExperienceRequestDto;
import com.knighttodo.character.rest.response.ExperienceResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExperienceRestMapper {

    ExperienceVO toExperienceVO(ExperienceRequestDto experienceRequestDto);

    ExperienceResponseDto toExperienceResponseDto(ExperienceVO experienceVO);
}
