package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;

import java.util.ArrayList;

public class TodoBlockFactory {

    public static final String BLOCK_NAME = "Sunday Todos";
    public static final String UPDATED_BLOCK_NAME = "Friday Todos";

    public static CreateTodoBlockRequestDto createTodoBlockRequestDto() {
        return CreateTodoBlockRequestDto
            .builder()
            .blockName(BLOCK_NAME)
            .build();
    }

    public static CreateTodoBlockRequestDto createTodoBlockRequestDtoWithoutName() {
        CreateTodoBlockRequestDto request = createTodoBlockRequestDto();
        request.setBlockName(null);
        return request;
    }

    public static CreateTodoBlockRequestDto createTodoBlockRequestDtoWithNameConsistingOfSpaces() {
        CreateTodoBlockRequestDto request = createTodoBlockRequestDto();
        request.setBlockName("    ");
        return request;
    }

    public static TodoBlock todoBlockInstance() {
        return TodoBlock
            .builder()
            .blockName(BLOCK_NAME)
            .todos(new ArrayList<>())
            .build();
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDto(TodoBlock todoBlock) {
        return UpdateTodoBlockRequestDto
            .builder()
            .id(todoBlock.getId())
            .blockName(UPDATED_BLOCK_NAME)
            .build();
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithoutId(TodoBlock todoBlock) {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto(todoBlock);
        request.setId(null);
        return request;
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithIdConsistingOfSpaces(TodoBlock todoBlock) {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto(todoBlock);
        request.setId("    ");
        return request;
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithoutName(TodoBlock todoBlock) {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto(todoBlock);
        request.setBlockName(null);
        return request;
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithNameConsistingOfSpaces(TodoBlock todoBlock) {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto(todoBlock);
        request.setBlockName("    ");
        return request;
    }
}
