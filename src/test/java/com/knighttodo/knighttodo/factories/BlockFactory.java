package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;
import com.knighttodo.knighttodo.rest.request.BlockRequestDto;

import java.util.ArrayList;

public class BlockFactory {

    public static final String BLOCK_NAME = "Sunday Todos";
    public static final String UPDATED_BLOCK_NAME = "Friday Todos";

    public static BlockRequestDto createBlockRequestDto() {
        return BlockRequestDto
            .builder()
            .blockName(BLOCK_NAME)
            .build();
    }

    public static BlockRequestDto createBlockRequestDtoWithoutName() {
        BlockRequestDto request = createBlockRequestDto();
        request.setBlockName(null);
        return request;
    }

    public static BlockRequestDto createBlockRequestDtoWithNameConsistingOfSpaces() {
        BlockRequestDto request = createBlockRequestDto();
        request.setBlockName("    ");
        return request;
    }

    public static Block BlockInstance() {
        return Block
            .builder()
            .blockName(BLOCK_NAME)
            .todos(new ArrayList<>())
            .build();
    }

    public static BlockRequestDto updateBlockRequestDto() {
        return BlockRequestDto
            .builder()
            .blockName(UPDATED_BLOCK_NAME)
            .build();
    }

    public static BlockRequestDto updateBlockRequestDtoWithoutName() {
        BlockRequestDto request = updateBlockRequestDto();
        request.setBlockName(null);
        return request;
    }

    public static BlockRequestDto updateBlockRequestDtoWithNameConsistingOfSpaces() {
        BlockRequestDto request = updateBlockRequestDto();
        request.setBlockName("    ");
        return request;
    }
}
