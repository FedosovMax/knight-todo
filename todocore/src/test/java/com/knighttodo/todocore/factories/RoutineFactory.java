package com.knighttodo.todocore.factories;

import com.knighttodo.todocore.gateway.privatedb.representation.Routine;
import com.knighttodo.todocore.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.gateway.privatedb.representation.enums.Scariness;
import com.knighttodo.todocore.rest.request.RoutineRequestDto;
import com.knighttodo.todocore.rest.response.RoutineResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoutineFactory {

    public static final String ROUTINE_NAME = "First routine";
    public static final Hardness HARDNESS_HARD = Hardness.HARD;
    public static final Scariness SCARINESS_HARD = Scariness.SCARY;
    public static final String UPDATED_ROUTINE_NAME = "Updated routine name";

    public static RoutineRequestDto createRoutineRequestDto() {
        return RoutineRequestDto.builder()
                .name(ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineInstanceIds(new ArrayList<>())
                .build();
    }

    public static RoutineRequestDto createRoutineWithInstanceIdsRequestDto(List<UUID> routineInstanceIds) {
        return RoutineRequestDto.builder()
                .name(ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineInstanceIds(routineInstanceIds)
                .build();
    }

    public static RoutineRequestDto createRoutineWithNullNameValueRequestDto() {
        return RoutineRequestDto.builder()
                .name(null)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineInstanceIds(new ArrayList<>())
                .build();
    }

    public static RoutineRequestDto updateRoutineRequestDto() {
        return RoutineRequestDto.builder()
                .name(UPDATED_ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineInstanceIds(new ArrayList<>())
                .build();
    }

    public static RoutineRequestDto updateRoutineRequestDtoWithInstanceIds(List<UUID> todoIds) {
        return RoutineRequestDto.builder()
                .name(ROUTINE_NAME)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineInstanceIds(todoIds)
                .build();
    }

    public static RoutineRequestDto updateRoutineWithNullNaveValueRequestDto() {
        return RoutineRequestDto.builder()
                .name(null)
                .hardness(HARDNESS_HARD)
                .scariness(SCARINESS_HARD)
                .routineInstanceIds(new ArrayList<>())
                .build();
    }

    public static RoutineResponseDto createRoutineResponseDto() {
        return RoutineResponseDto
                .builder()
                .hardness(Hardness.HARD)
                .scariness(Scariness.SCARY)
                .name(ROUTINE_NAME)
                .ready(false)
                .routineInstances(new ArrayList<>())
                .build();
    }

    public static Routine routineInstance() {
        return Routine
                .builder()
                .hardness(Hardness.HARD)
                .name(ROUTINE_NAME)
                .scariness(Scariness.SCARY)
                .routineInstances(new ArrayList<>())
                .build();
    }
}
