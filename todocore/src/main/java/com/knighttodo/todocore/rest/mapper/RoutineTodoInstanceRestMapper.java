package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.rest.request.RoutineTodoRequestDto;
import com.knighttodo.todocore.rest.response.RoutineTodoInstanceReadyResponseDto;
import com.knighttodo.todocore.rest.response.RoutineTodoInstanceResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Named("RoutineTodoRestMapper")
@Mapper(componentModel = "spring")
public interface RoutineTodoInstanceRestMapper {

    @Named("toRoutineTodoInstanceResponseDto")
    @Mapping(target = "routineInstanceId", source = "routineInstanceVO.id")
    RoutineTodoInstanceResponseDto toRoutineTodoInstanceResponseDto(RoutineTodoInstanceVO routineTodoInstanceVO);

    RoutineTodoInstanceVO toRoutineTodoInstanceVO(RoutineTodoRequestDto requestDto);

    @Named("fromIdToRoutineTodoInstanceVOWithId")
    @Mapping(target = "id", source = "routineTodoInstanceId")
    RoutineTodoInstanceVO toRoutineTodoInstanceVOFromRoutineId(UUID routineTodoInstanceId);

    @Mapping(target = "routineId", source = "routineInstanceVO.id")
    RoutineTodoInstanceReadyResponseDto toRoutineTodoInstanceReadyResponseDto(RoutineTodoInstanceVO routineTodoInstanceVO);

}
