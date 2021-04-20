package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.rest.request.DayRequestDto;
import com.knighttodo.knighttodo.rest.response.DayResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DayRestMapper {

    DayVO toDayVO(DayRequestDto requestDto);

    DayResponseDto toDayResponseDto(DayVO dayVO);
}
