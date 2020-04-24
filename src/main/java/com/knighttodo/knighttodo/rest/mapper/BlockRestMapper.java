package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.rest.request.BlockRequestDto;
import com.knighttodo.knighttodo.rest.response.BlockResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoutineRestMapper.class)
public interface BlockRestMapper {

    BlockVO toBlockVO(BlockRequestDto requestDto);

    BlockResponseDto toBlockResponseDto(BlockVO blockVO);
}
