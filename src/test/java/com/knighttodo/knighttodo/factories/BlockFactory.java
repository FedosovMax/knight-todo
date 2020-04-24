package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;
import com.knighttodo.knighttodo.rest.dto.block.request.CreateBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.block.request.UpdateBlockRequestDto;

import java.util.ArrayList;

public class BlockFactory {

    public static final String BLOCK_NAME = "Sunday Todos";
    public static final String UPDATED_BLOCK_NAME = "Friday Todos";

    public static CreateBlockRequestDto createBlockRequestDto() {
        return CreateBlockRequestDto
            .builder()
            .blockName(BLOCK_NAME)
            .build();
    }

    public static CreateBlockRequestDto createBlockRequestDtoWithoutName() {
        CreateBlockRequestDto request = createBlockRequestDto();
        request.setBlockName(null);
        return request;
    }

    public static CreateBlockRequestDto createBlockRequestDtoWithNameConsistingOfSpaces() {
        CreateBlockRequestDto request = createBlockRequestDto();
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

    public static UpdateBlockRequestDto updateBlockRequestDto() {
        return UpdateBlockRequestDto
            .builder()
            .blockName(UPDATED_BLOCK_NAME)
            .build();
    }

    public static UpdateBlockRequestDto updateBlockRequestDtoWithoutName() {
        UpdateBlockRequestDto request = updateBlockRequestDto();
        request.setBlockName(null);
        return request;
    }

    public static UpdateBlockRequestDto updateBlockRequestDtoWithNameConsistingOfSpaces() {
        UpdateBlockRequestDto request = updateBlockRequestDto();
        request.setBlockName("    ");
        return request;
    }
}
