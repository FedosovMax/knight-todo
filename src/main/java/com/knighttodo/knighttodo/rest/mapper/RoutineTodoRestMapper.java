package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineTodoRestMapper")
@Mapper(componentModel = "spring")
public interface RoutineTodoRestMapper {

    @Mapping(target = "routineId", source = "routine.id")
    RoutineTodoResponseDto toRoutineTodoResponseDto(RoutineTodoVO dayTodoVO);

    RoutineTodoVO toRoutineTodoVO(RoutineTodoRequestDto requestDto);

    @Named("fromIdToRoutineTodoVOWithId")
    @Mapping(target = "id", source = "routineTodoId")
    RoutineTodoVO toRoutineTodoVOFromRoutineId(String routineTodoId);

    @Mapping(target = "routineId", source = "routine.id")
    RoutineTodoReadyResponseDto toRoutineTodoReadyResponseDto(RoutineTodoVO routineTodoVO);
}
