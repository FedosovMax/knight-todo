package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.dto.routine.request.CreateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.request.UpdateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.CreateRoutineResponseDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.RoutineResponseDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.UpdateRoutineResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoutineRestMapper {

    RoutineVO toRoutineVO(CreateRoutineRequestDto requestDto);

    CreateRoutineResponseDto toCreateRoutineResponseDto(RoutineVO routineVO);

    RoutineVO toRoutineVO(UpdateRoutineRequestDto requestDto);

    UpdateRoutineResponseDto toUpdateRoutineResponseDto(RoutineVO routineVO);

    RoutineResponseDto toRoutineResponseDto(RoutineVO routineVO);
}
