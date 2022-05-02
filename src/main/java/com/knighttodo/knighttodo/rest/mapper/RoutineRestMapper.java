package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
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
