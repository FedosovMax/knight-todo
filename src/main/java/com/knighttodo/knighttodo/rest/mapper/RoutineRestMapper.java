package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoutineTodoRestMapper.class)
public interface RoutineRestMapper {

    @Mapping(source = "routineTodoIds", target = "routineTodos",
            qualifiedByName = {"RoutineTodoRestMapper", "fromIdToRoutineTodoVOWithId"})
    RoutineVO toRoutineVO(RoutineRequestDto requestDto);

    RoutineResponseDto toRoutineResponseDto(RoutineVO routineVO);
}
