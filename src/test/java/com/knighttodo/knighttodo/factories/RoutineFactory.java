package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;

import java.util.ArrayList;
import java.util.List;

public class RoutineFactory {

    public static final String ROUTINE_NAME = "First routine";
    public static final Hardness HARDNESS_HARD = Hardness.HARD;
    public static final Scariness SCARINESS_HARD = Scariness.SCARY;
    public static final String UPDATED_ROUTINE_NAME = "Updated routine name";
    public static final String UPDATED_BLOCK_NAME = "Friday Todos";

    public static RoutineRequestDto createRoutineRequestDto() {
        return RoutineRequestDto.builder()
            .name(ROUTINE_NAME)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .todoIds(new ArrayList<>())
            .build();
    }

    public static RoutineRequestDto createRoutineWithTodoIdsRequestDto(List<String> todoIds) {
        return RoutineRequestDto.builder()
            .name(ROUTINE_NAME)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .todoIds(todoIds)
            .build();
    }

    public static RoutineRequestDto createRoutineWithNullNameValueRequestDto() {
        return RoutineRequestDto.builder()
            .name(null)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .todoIds(new ArrayList<>())
            .build();
    }

    public static RoutineRequestDto updateRoutineRequestDto() {
        return RoutineRequestDto.builder()
            .name(UPDATED_ROUTINE_NAME)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .ready(true)
            .todoIds(new ArrayList<>())
            .build();
    }

    public static RoutineRequestDto updateRoutineRequestDtoWithTodoIds(List<String> todoIds) {
        return RoutineRequestDto.builder()
            .name(ROUTINE_NAME)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .ready(true)
            .todoIds(todoIds)
            .build();
    }

    public static RoutineRequestDto updateRoutineWithNullNaveValueRequestDto() {
        return RoutineRequestDto.builder()
            .name(null)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .ready(true)
            .todoIds(new ArrayList<>())
            .build();
    }

    public static RoutineResponseDto createRoutineResponseDto() {
        return RoutineResponseDto
            .builder()
            .hardness(Hardness.HARD)
            .scariness(Scariness.SCARY)
            .name(ROUTINE_NAME)
            .ready(false)
            .todos(new ArrayList<>())
            .build();
    }

    public static Routine routineInstance() {
        return Routine
            .builder()
            .hardness(Hardness.HARD)
            .name(ROUTINE_NAME)
            .scariness(Scariness.SCARY)
            .todos(new ArrayList<>())
            .build();
    }
}
