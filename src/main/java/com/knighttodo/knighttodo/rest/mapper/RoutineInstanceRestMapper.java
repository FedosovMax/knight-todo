package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.request.RoutineInstanceRequestDto;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineInstanceReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineInstanceResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoReadyResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = RoutineTodoRestMapper.class)
public interface RoutineInstanceRestMapper {

    @Mapping(source = "routineTodoIds", target = "routineTodos",
            qualifiedByName = {"RoutineTodoRestMapper", "fromIdToRoutineTodoVOWithId"})
    RoutineInstanceVO toRoutineInstanceVO(RoutineInstanceRequestDto requestDto);

    RoutineInstanceResponseDto toRoutineInstanceResponseDto(RoutineInstanceVO routineVO);

    @Named("fromIdToRoutineInstanceVOWithId")
    @Mapping(target = "id", source = "routineInstanceId")
    RoutineInstanceVO toRoutineTodoVOFromRoutineId(String routineInstanceId);

    @Mapping(target = "routineId", source = "routine.id")
    RoutineInstanceReadyResponseDto toRoutineInstanceReadyResponseDto(RoutineInstanceVO routineInstanceVO);
}
