package com.knighttodo.knighttodo.gateway.experience.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.experience.request.ExperienceRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoVOMapper {

    @Mapping(target = "todoId", source = "id")
    @Mapping(target = "userId", ignore = true)
    ExperienceRequest toExperienceRequest(TodoVO todoVO);
}
