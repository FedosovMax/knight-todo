package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.dto.routine.request.CreateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.request.UpdateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.RoutineResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoutineRestMapper {

    RoutineVO toRoutineVO(CreateRoutineRequestDto requestDto);

    RoutineVO toRoutineVO(UpdateRoutineRequestDto requestDto);

    @Mapping(source = "block.id", target = "blockId")
    RoutineResponseDto toRoutineResponseDto(RoutineVO routineVO);
}
