package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TodoRestMapper.class)
public interface RoutineRestMapper {

    @Mapping(source = "todoIds", target = "todos", qualifiedByName = "fromIdToTodoVOWithId")
    RoutineVO toRoutineVO(RoutineRequestDto requestDto);

    @Mapping(source = "block.id", target = "blockId")
    RoutineResponseDto toRoutineResponseDto(RoutineVO routineVO);
}
