package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;
import java.util.ArrayList;

public class TodoFactory {

    public static final String TODO_NAME = "Write integration tests";
    public static final Scaryness SCARYNESS_TODO = Scaryness.fromText("NOT_SCARY");
    public static final Hardness HARDNESS_TODO = Hardness.fromText("EXTRAORDINARY");
    public static final boolean FALSE_TODO_READY = false;
    public static final boolean TRUE_TODO_READY = true;
    public static final String UPDATED_TODO_NAME = "Write more integration tests";
    public static final Scaryness UPDATED_SCARYNESS_TODO = Scaryness.fromText("SCARY");
    public static final Hardness UPDATED_HARDNESS_TODO = Hardness.fromText("HARD");

    public static final String BLOCK_NAME = "Sunday Todos";
    public static final String UPDATED_BLOCK_NAME = "Friday Todos";

    private TodoFactory() {
    }

    public static CreateTodoRequestDto createTodoRequestDto(TodoBlock savedTodoBlock) {
        return CreateTodoRequestDto
            .builder()
            .todoName(TODO_NAME)
            .scaryness(SCARYNESS_TODO)
            .hardness(HARDNESS_TODO)
            .isReady(FALSE_TODO_READY)
            .build();
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutName(TodoBlock savedTodoBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedTodoBlock);
        request.setTodoName(null);
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithNameConsistingOfSpaces(TodoBlock savedTodoBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedTodoBlock);
        request.setTodoName("    ");
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutScaryness(TodoBlock savedTodoBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedTodoBlock);
        request.setScaryness(null);
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutHardness(TodoBlock savedTodoBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedTodoBlock);
        request.setHardness(null);
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutTodoBlockId() {
        return CreateTodoRequestDto
            .builder()
            .todoName(TODO_NAME)
            .scaryness(SCARYNESS_TODO)
            .hardness(HARDNESS_TODO)
            .isReady(FALSE_TODO_READY)
            .build();
    }

    public static Todo todoWithBlockIdInstance(TodoBlock todoBlock) {
        return Todo
            .builder()
            .todoName(TODO_NAME)
            .scaryness(SCARYNESS_TODO)
            .hardness(HARDNESS_TODO)
            .todoBlock(todoBlock)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static Todo todoWithBlockIdReadyInstance(TodoBlock todoBlock) {
        return Todo
            .builder()
            .todoName(TODO_NAME)
            .scaryness(SCARYNESS_TODO)
            .hardness(HARDNESS_TODO)
            .todoBlock(todoBlock)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestDto(TodoBlock savedTodoBlock) {
        return UpdateTodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scaryness(UPDATED_SCARYNESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithoutName(TodoBlock savedTodoBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedTodoBlock);
        request.setTodoName(null);
        return request;
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithNameConsistingOfSpaces(TodoBlock savedTodoBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedTodoBlock);
        request.setTodoName("    ");
        return request;
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithoutScaryness(TodoBlock savedTodoBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedTodoBlock);
        request.setScaryness(null);
        return request;
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithoutHardness(TodoBlock savedTodoBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedTodoBlock);
        request.setHardness(null);
        return request;
    }

    public static TodoBlock notSavedUpdatedTodoBlock() {
        return TodoBlock
            .builder()
            .blockName(BLOCK_NAME)
            .todos(new ArrayList<>())
            .build();
    }

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

    public static Todo notSavedUpdatedTodo(TodoBlock todoBlock) {
        return Todo
            .builder()
            .todoName(UPDATED_TODO_NAME)
            .scaryness(UPDATED_SCARYNESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .todoBlock(todoBlock)
            .ready(FALSE_TODO_READY)
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

    public static UpdateTodoBlockRequestDto updateTodoBlockRequestDtoWithoutTodos(TodoBlock todoBlock) {
        UpdateTodoBlockRequestDto request = updateTodoBlockRequestDto(todoBlock);
        return request;
    }
}
