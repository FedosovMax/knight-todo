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

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDto() {
        return UpdateTodoBlockRequestDto
            .builder()
            .blockName(UPDATED_BLOCK_NAME)
            .build();
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithoutName() {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto();
        request.setBlockName(null);
        return request;
    }

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithNameConsistingOfSpaces() {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto();
        request.setBlockName("    ");
        return request;
    }
}
