package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.RoutineVO;
import com.knighttodo.todocore.rest.request.RoutineRequestDto;
import com.knighttodo.todocore.rest.response.RoutineResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = RoutineInstanceRestMapper.class)
public interface RoutineRestMapper {

    @Mapping(source = "routineInstanceIds", target = "routineInstanceVOs",
            qualifiedByName = {"fromIdToRoutineInstanceVOWithId"})
    RoutineVO toRoutineVO(RoutineRequestDto requestDto);

    @Mapping(source = "routineInstanceVOs", target = "routineInstances")
    RoutineResponseDto toRoutineResponseDto(RoutineVO routineVO);
}
