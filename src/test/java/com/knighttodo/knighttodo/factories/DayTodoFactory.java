package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.service.expirience.response.ExperienceResponse;
import com.knighttodo.knighttodo.service.privatedb.representation.Day;
import com.knighttodo.knighttodo.service.privatedb.representation.DayTodo;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import com.knighttodo.knighttodo.rest.request.DayTodoRequestDto;

import java.util.UUID;

public class DayTodoFactory {

    public static final String TODO_NAME = "Write integration tests";
    public static final Scariness SCARINESS_TODO = Scariness.NOT_SCARY;
    public static final Hardness HARDNESS_TODO = Hardness.EXTRAORDINARY;
    public static final boolean FALSE_TODO_READY = false;
    public static final boolean TRUE_TODO_READY = true;
    public static final String UPDATED_TODO_NAME = "Write more integration tests";
    public static final int HARD_SCARY_EXPERIENCE = 37;
    public static final Scariness UPDATED_SCARINESS_TODO = Scariness.SCARY;
    public static final Hardness UPDATED_HARDNESS_TODO = Hardness.HARD;

    private DayTodoFactory() {
    }

    public static DayTodoRequestDto createDayTodoRequestDto() {
        return DayTodoRequestDto
            .builder()
            .dayTodoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static DayTodoRequestDto createDayTodoRequestDtoWithoutName() {
        DayTodoRequestDto request = createDayTodoRequestDto();
        request.setDayTodoName(null);
        return request;
    }

    public static DayTodoRequestDto createDayTodoRequestDtoWithNameConsistingOfSpaces() {
        DayTodoRequestDto request = createDayTodoRequestDto();
        request.setDayTodoName("    ");
        return request;
    }

    public static DayTodoRequestDto createDayTodoRequestDtoWithoutScariness() {
        DayTodoRequestDto request = createDayTodoRequestDto();
        request.setScariness(null);
        return request;
    }

    public static DayTodoRequestDto createDayTodoRequestDtoWithoutHardness() {
        DayTodoRequestDto request = createDayTodoRequestDto();
        request.setHardness(null);
        return request;
    }

    public static DayTodo dayTodoWithDayInstance(Day day) {
        return DayTodo
            .builder()
            .dayTodoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .day(day)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static DayTodo dayTodoWithDayReadyInstance(Day day) {
        return DayTodo
            .builder()
            .dayTodoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .day(day)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestDto(Day savedDay) {
        return DayTodoRequestDto.builder()
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestReadyDto() {
        return DayTodoRequestDto.builder()
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestReadyDtoWithChangedScariness() {
        return DayTodoRequestDto.builder()
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestReadyDtoWithChangedHardness() {
        return DayTodoRequestDto.builder()
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestDtoWithoutName(Day savedDay) {
        DayTodoRequestDto request = updateDayTodoRequestDto(savedDay);
        request.setDayTodoName(null);
        return request;
    }

    public static DayTodoRequestDto updateDayTodoRequestDtoWithNameConsistingOfSpaces(Day savedDay) {
        DayTodoRequestDto request = updateDayTodoRequestDto(savedDay);
        request.setDayTodoName("    ");
        return request;
    }

    public static DayTodoRequestDto updateDayTodoRequestDtoWithoutScariness(Day savedDay) {
        DayTodoRequestDto request = updateDayTodoRequestDto(savedDay);
        request.setScariness(null);
        return request;
    }

    public static DayTodoRequestDto updateDayTodoRequestDtoWithoutHardness(Day savedDay) {
        DayTodoRequestDto request = updateDayTodoRequestDto(savedDay);
        request.setHardness(null);
        return request;
    }

    public static ExperienceResponse experienceResponseInstance(UUID todoId) {
        return ExperienceResponse.builder().todoId(todoId).experience(HARD_SCARY_EXPERIENCE).build();
    }
}
