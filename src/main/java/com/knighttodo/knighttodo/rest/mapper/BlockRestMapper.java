package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.rest.dto.block.request.CreateBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.block.request.UpdateBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.block.response.CreateBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.block.response.BlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.block.response.UpdateBlockResponseDto;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockRestMapper {

    BlockVO toBlockVO(CreateBlockRequestDto requestDto);

    CreateBlockResponseDto toCreateBlockResponseDto(BlockVO blockVO);

    BlockVO toBlockVO(UpdateBlockRequestDto requestDto);

    UpdateBlockResponseDto toUpdateBlockResponseDto(BlockVO blockVO);

    BlockResponseDto toBlockResponseDto(BlockVO blockVO);
}
