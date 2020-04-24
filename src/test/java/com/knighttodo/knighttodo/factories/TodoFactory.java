package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;

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

    public static CreateTodoRequestDto createTodoRequestDto(Block savedBlock) {
        return CreateTodoRequestDto
            .builder()
            .todoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .isReady(FALSE_TODO_READY)
            .build();
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutName(Block savedBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setTodoName(null);
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithNameConsistingOfSpaces(Block savedBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setTodoName("    ");
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutScariness(Block savedBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setScariness(null);
        return request;
    }

    public static CreateTodoRequestDto createTodoRequestDtoWithoutHardness(Block savedBlock) {
        CreateTodoRequestDto request = createTodoRequestDto(savedBlock);
        request.setHardness(null);
        return request;
    }

    public static Todo todoWithBlockIdInstance(Block block) {
        return Todo
            .builder()
            .todoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .block(block)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static Todo todoWithBlockIdReadyInstance(Block block) {
        return Todo
            .builder()
            .todoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .block(block)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestDto(Block savedBlock) {
        return UpdateTodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestReadyDto() {
        return UpdateTodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestReadyDtoWithChangedScariness() {
        return UpdateTodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestReadyDtoWithChangedHardness() {
        return UpdateTodoRequestDto.builder()
            .todoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithoutName(Block savedBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setTodoName(null);
        return request;
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithNameConsistingOfSpaces(Block savedBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setTodoName("    ");
        return request;
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithoutScariness(Block savedBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setScariness(null);
        return request;
    }

    public static UpdateTodoRequestDto updateTodoRequestDtoWithoutHardness(Block savedBlock) {
        UpdateTodoRequestDto request = updateTodoRequestDto(savedBlock);
        request.setHardness(null);
        return request;
    }

    public static ExperienceResponse experienceResponseInstance(String todoId) {
        return ExperienceResponse.builder().todoId(todoId).experience(HARD_SCARY_EXPERIENCE).build();
    }
}
