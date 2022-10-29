package com.knighttodo.todocore.rest.mapper;

import com.knighttodo.todocore.domain.DayVO;
import com.knighttodo.todocore.rest.request.DayRequestDto;
import com.knighttodo.todocore.rest.response.DayResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DayRestMapper {

    DayVO toDayVO(DayRequestDto requestDto);

    DayResponseDto toDayResponseDto(DayVO dayVO);
}
