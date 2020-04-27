package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.request.TodoRequestDto;

public class TodoFactory {

    public static final String TODO_NAME = "Write integration tests";
    public static final Scariness SCARINESS_TODO = Scariness.NOT_SCARY;
    public static final Hardness HARDNESS_TODO = Hardness.EXTRAORDINARY;
    public static final boolean FALSE_TODO_READY = false;
    public static final boolean TRUE_TODO_READY = true;
    public static final String UPDATED_TODO_NAME = "Write more integration tests";
    public static final Integer HARD_SCARY_EXPERIENCE = 37;
    public static final Scariness UPDATED_SCARINESS_TODO = Scariness.SCARY;
    public static final Hardness UPDATED_HARDNESS_TODO = Hardness.HARD;

    private TodoFactory() {
    }

    public static TodoRequestDto createTodoRequestDto(Block savedBlock) {
        return TodoRequestDto
            .builder()
            .todoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static TodoRequestDto createTodoRequestDtoWithoutName(Block savedBlock) {
        TodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setTodoName(null);
        return request;
    }

    public static TodoRequestDto createTodoRequestDtoWithNameConsistingOfSpaces(Block savedBlock) {
        TodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setTodoName("    ");
        return request;
    }

    public static TodoRequestDto createTodoRequestDtoWithoutScariness(Block savedBlock) {
        TodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setScariness(null);
        return request;
    }

    public static TodoRequestDto createTodoRequestDtoWithoutHardness(Block savedBlock) {
        TodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setHardness(null);
        return request;
    }

    public static Todo todoWithBlockInstance(Block block) {
        return Todo
            .builder()
            .todoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .block(block)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static Todo todoWithBlockReadyInstance(Block block) {
        return Todo
            .builder()
            .todoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .block(block)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static TodoRequestDto updateTodoRequestDto(Block savedBlock) {
        return TodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static TodoRequestDto updateTodoRequestReadyDto() {
        return TodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static TodoRequestDto updateTodoRequestReadyDtoWithChangedScariness() {
        return TodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static TodoRequestDto updateTodoRequestReadyDtoWithChangedHardness() {
        return TodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static TodoRequestDto updateTodoRequestDtoWithoutName(Block savedBlock) {
        TodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setTodoName(null);
        return request;
    }

    public static TodoRequestDto updateTodoRequestDtoWithNameConsistingOfSpaces(Block savedBlock) {
        TodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setTodoName("    ");
        return request;
    }

    public static TodoRequestDto updateTodoRequestDtoWithoutScariness(Block savedBlock) {
        TodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setScariness(null);
        return request;
    }

    public static TodoRequestDto updateTodoRequestDtoWithoutHardness(Block savedBlock) {
        TodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setHardness(null);
        return request;
    }

    public static ExperienceResponse experienceResponseInstance(String todoId) {
        return ExperienceResponse.builder().todoId(todoId).experience(HARD_SCARY_EXPERIENCE).build();
    }
}
