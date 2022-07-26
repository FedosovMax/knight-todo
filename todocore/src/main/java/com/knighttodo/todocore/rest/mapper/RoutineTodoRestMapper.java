package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.rest.request.RoutineTodoRequestDto;
import com.knighttodo.todocore.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.todocore.rest.response.RoutineTodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Named("RoutineTodoRestMapper")
@Mapper(componentModel = "spring")
public interface RoutineTodoRestMapper {

    @Mapping(target = "routineId", source = "routineVO.id")
    RoutineTodoResponseDto toRoutineTodoResponseDto(RoutineTodoVO dayTodoVO);

    RoutineTodoVO toRoutineTodoVO(RoutineTodoRequestDto requestDto);

    @Named("fromIdToRoutineTodoVOWithId")
    @Mapping(target = "id", source = "routineTodoId")
    RoutineTodoVO toRoutineTodoVOFromRoutineId(UUID routineTodoId);

    @Mapping(target = "routineId", source = "routineVO.id")
    RoutineTodoReadyResponseDto toRoutineTodoReadyResponseDto(RoutineTodoVO routineTodoVO);
}
