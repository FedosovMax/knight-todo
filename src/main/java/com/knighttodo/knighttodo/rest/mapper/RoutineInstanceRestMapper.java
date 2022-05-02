package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.rest.request.RoutineInstanceRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineInstanceReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineInstanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = RoutineTodoInstanceRestMapper.class)
public interface RoutineInstanceRestMapper {

    @Mapping(target = "routineTodoInstances", source = "routineTodoInstancesIds",
            qualifiedByName = {"fromIdToRoutineTodoInstanceVOWithId"})
    RoutineInstanceVO toRoutineInstanceVO(RoutineInstanceRequestDto requestDto);

    @Mapping(target = "routineTodoInstances", source = "routineTodoInstances", qualifiedByName = "toRoutineTodoInstanceResponseDto")
    RoutineInstanceResponseDto toRoutineInstanceResponseDto(RoutineInstanceVO routineVO);

    @Named("fromIdToRoutineInstanceVOWithId")
    @Mapping(target = "id", source = "routineInstanceId")
    RoutineInstanceVO toRoutineInstanceVOFromRoutineInstanceId(UUID routineInstanceId);

    @Mapping(target = "routineId", source = "routine.id")
    RoutineInstanceReadyResponseDto toRoutineInstanceReadyResponseDto(RoutineInstanceVO routineInstanceVO);
}
