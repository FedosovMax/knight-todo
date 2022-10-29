package com.knighttodo.knighttodo.rest.request.mapper;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoInstanceReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoInstanceResponseDto;
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
