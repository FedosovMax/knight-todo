package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;

import java.util.UUID;

public class RoutineTodoFactory {

    public static final String ROUTINE_TODO_NAME = "Write integration tests";
    public static final Scariness SCARINESS_TODO = Scariness.NOT_SCARY;
    public static final Hardness HARDNESS_TODO = Hardness.EXTRAORDINARY;
    public static final boolean FALSE_TODO_READY = false;
    public static final boolean TRUE_TODO_READY = true;
    public static final String UPDATED_ROUTINE_TODO_NAME = "Write more integration tests";
    public static final int HARD_SCARY_EXPERIENCE = 37;
    public static final Scariness UPDATED_SCARINESS_TODO = Scariness.SCARY;
    public static final Hardness UPDATED_HARDNESS_TODO = Hardness.HARD;

    private RoutineTodoFactory() {
    }

    public static RoutineTodoRequestDto createRoutineTodoRequestDto() {
        return RoutineTodoRequestDto
            .builder()
            .routineTodoName(ROUTINE_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static RoutineTodoRequestDto createRoutineTodoRequestDtoWithoutName() {
        RoutineTodoRequestDto request = createRoutineTodoRequestDto();
        request.setRoutineTodoName(null);
        return request;
    }

    public static RoutineTodoRequestDto createRoutineTodoRequestDtoWithNameConsistingOfSpaces() {
        RoutineTodoRequestDto request = createRoutineTodoRequestDto();
        request.setRoutineTodoName("    ");
        return request;
    }

    public static RoutineTodoRequestDto createRoutineTodoRequestDtoWithoutScariness() {
        RoutineTodoRequestDto request = createRoutineTodoRequestDto();
        request.setScariness(null);
        return request;
    }

    public static RoutineTodoRequestDto createRoutineTodoRequestDtoWithoutHardness() {
        RoutineTodoRequestDto request = createRoutineTodoRequestDto();
        request.setHardness(null);
        return request;
    }

    public static RoutineTodo routineTodoWithRoutineInstance(RoutineInstance routineInstance) {
        return RoutineTodo
            .builder()
            .routineTodoName(ROUTINE_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .routineInstance(routineInstance)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static RoutineTodo routineTodoWithRoutineReadyInstance(RoutineInstance routineInstance) {
        return RoutineTodo
            .builder()
            .routineTodoName(ROUTINE_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .routineInstance(routineInstance)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestDto(Routine savedRoutine) {
        return RoutineTodoRequestDto.builder()
            .routineTodoName(UPDATED_ROUTINE_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestReadyDto() {
        return RoutineTodoRequestDto.builder()
            .routineTodoName(UPDATED_ROUTINE_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestReadyDtoWithChangedScariness() {
        return RoutineTodoRequestDto.builder()
            .routineTodoName(UPDATED_ROUTINE_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestReadyDtoWithChangedHardness() {
        return RoutineTodoRequestDto.builder()
            .routineTodoName(UPDATED_ROUTINE_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestDtoWithoutName(Routine savedRoutine) {
        RoutineTodoRequestDto request = updateRoutineTodoRequestDto(savedRoutine);
        request.setRoutineTodoName(null);
        return request;
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestDtoWithNameConsistingOfSpaces(Routine savedRoutine) {
        RoutineTodoRequestDto request = updateRoutineTodoRequestDto(savedRoutine);
        request.setRoutineTodoName("    ");
        return request;
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestDtoWithoutScariness(Routine savedRoutine) {
        RoutineTodoRequestDto request = updateRoutineTodoRequestDto(savedRoutine);
        request.setScariness(null);
        return request;
    }

    public static RoutineTodoRequestDto updateRoutineTodoRequestDtoWithoutHardness(Routine savedRoutine) {
        RoutineTodoRequestDto request = updateRoutineTodoRequestDto(savedRoutine);
        request.setHardness(null);
        return request;
    }

    public static ExperienceResponse experienceResponseInstance(UUID todoId) {
        return ExperienceResponse.builder().todoId(todoId).experience(HARD_SCARY_EXPERIENCE).build();
    }
}
