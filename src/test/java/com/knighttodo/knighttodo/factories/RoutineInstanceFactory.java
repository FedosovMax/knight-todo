package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.request.RoutineInstanceRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineInstanceResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoutineInstanceFactory {

    public static final String ROUTINE_NAME = "First routine";
    public static final Hardness HARDNESS_HARD = Hardness.HARD;
    public static final Scariness SCARINESS_HARD = Scariness.SCARY;
    public static final String UPDATED_ROUTINE_NAME = "Updated routine name";

    public static RoutineInstanceRequestDto createRoutineInstanceRequestDto() {
        return RoutineInstanceRequestDto.builder()
                .name(ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineTodoIds(new ArrayList<>())
                .build();
    }

    public static RoutineInstanceRequestDto createRoutineInstanceWithTodoIdsRequestDto(List<String> todoIds) {
        return RoutineInstanceRequestDto.builder()
                .name(ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineTodoIds(todoIds)
                .build();
    }

    public static RoutineInstanceRequestDto createRoutineInstanceWithNullNameValueRequestDto() {
        return RoutineInstanceRequestDto.builder()
                .name(null)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineTodoIds(new ArrayList<>())
                .build();
    }

    public static RoutineInstanceRequestDto updateRoutineInstanceRequestDto() {
        return RoutineInstanceRequestDto.builder()
                .name(UPDATED_ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .ready(true)
                .routineTodoIds(new ArrayList<>())
                .build();
    }

    public static RoutineInstanceRequestDto updateRoutineInstanceRequestDtoWithTodoIds(List<String> todoIds) {
        return RoutineInstanceRequestDto.builder()
                .name(ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .ready(true)
                .routineTodoIds(todoIds)
                .build();
    }

    public static RoutineInstanceRequestDto updateRoutineInstanceWithNullNaveValueRequestDto() {
        return RoutineInstanceRequestDto.builder()
                .name(null)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .ready(true)
                .routineTodoIds(new ArrayList<>())
                .build();
    }

    public static RoutineInstanceResponseDto createRoutineInstanceResponseDto() {
        return RoutineInstanceResponseDto
                .builder()
                .hardness(Hardness.HARD)
                .scariness(Scariness.SCARY)
                .name(ROUTINE_NAME)
                .ready(false)
                .routineTodos(new ArrayList<>())
                .build();
    }

    public static RoutineInstance routineInstance() {
        return RoutineInstance
                .builder()
                .hardness(Hardness.HARD)
                .name(ROUTINE_NAME)
                .scariness(Scariness.SCARY)
                .routineTodos(new ArrayList<>())
                .build();
    }

    public static RoutineInstance routineInstanceWithRoutine(Routine routine) {
        return RoutineInstance
                .builder()
                .id(UUID.randomUUID())
                .hardness(Hardness.HARD)
                .name(ROUTINE_NAME)
                .scariness(Scariness.SCARY)
                .routine(routine)
                .routineTodos(new ArrayList<>())
                .build();
    }
}
