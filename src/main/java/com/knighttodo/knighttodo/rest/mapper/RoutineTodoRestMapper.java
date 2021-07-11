package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.UUID;

@Named("RoutineTodoRestMapper")
@Mapper(componentModel = "spring")
public interface RoutineTodoRestMapper {

    @Mapping(target = "routineId", source = "routineInstanceVO.id")
    RoutineTodoResponseDto toRoutineTodoResponseDto(RoutineTodoVO dayTodoVO);

    RoutineTodoVO toRoutineTodoVO(RoutineTodoRequestDto requestDto);

    @Named("fromIdToRoutineTodoVOWithId")
    @Mapping(target = "id", source = "routineTodoId")
    RoutineTodoVO toRoutineTodoVOFromRoutineId(UUID routineTodoId);

    @Mapping(target = "routineId", source = "routineInstanceVO.id")
    RoutineTodoReadyResponseDto toRoutineTodoReadyResponseDto(RoutineTodoVO routineTodoVO);
}
