package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.dto.routine.request.CreateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.request.UpdateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.RoutineResponseDto;

import java.util.ArrayList;

public class RoutineFactory {

    public static final String ROUTINE_NAME = "First routine";
    public static final Hardness HARDNESS_HARD = Hardness.HARD;
    public static final Scariness SCARINESS_HARD = Scariness.SCARY;
    public static final String UPDATED_BLOCK_NAME = "Friday Todos";

    public static CreateRoutineRequestDto createRoutineRequestDto() {
        return CreateRoutineRequestDto.builder()
            .name(ROUTINE_NAME)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .build();
    }

    public static CreateRoutineRequestDto createRoutineWithNullNameValueRequestDto() {
        return CreateRoutineRequestDto.builder()
            .name(null)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .build();
    }

    public static UpdateRoutineRequestDto updateRoutineRequestDto() {
        return UpdateRoutineRequestDto.builder()
            .name(ROUTINE_NAME)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .ready(true)
            .build();
    }

    public static UpdateRoutineRequestDto updateRoutineWithNullNaveValueRequestDto() {
        return UpdateRoutineRequestDto.builder()
            .name(null)
            .hardness(HARDNESS_HARD)
            .scariness(SCARINESS_HARD)
            .ready(true)
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

    public static Routine createRoutineInstance() {
        return Routine
            .builder()
            .hardness(Hardness.HARD)
            .name(ROUTINE_NAME)
            .scariness(Scariness.SCARY)
            .todos(new ArrayList<>())
            .build();
    }
}
