package com.knighttodo.knighttodo.rest.request.mapper;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.rest.request.DayRequestDto;
import com.knighttodo.knighttodo.rest.response.DayResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DayRestMapper {

    DayVO toDayVO(DayRequestDto requestDto);

    DayResponseDto toDayResponseDto(DayVO dayVO);
}
