package com.knighttodo.todocore.factories;


import com.knighttodo.todocore.rest.request.DayTodoRequestDto;
import com.knighttodo.todocore.service.privatedb.representation.Day;
import com.knighttodo.todocore.service.privatedb.representation.DayTodo;
import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;

public class DayTodoFactory {

    public static final String TODO_NAME = "Write integration tests";
    public static final Scariness SCARINESS_TODO = Scariness.NOT_SCARY;
    public static final Hardness HARDNESS_TODO = Hardness.EXTRAORDINARY;
    public static final String COLOR_TODO = "A5FFC9";
    public static final boolean FALSE_TODO_READY = false;
    public static final boolean TRUE_TODO_READY = true;
    public static final int TODO_ORDER_NUMBER = 111;
    public static final String UPDATED_TODO_NAME = "Write more integration tests";
    public static final int HARD_SCARY_EXPERIENCE = 37;
    public static final Scariness UPDATED_SCARINESS_TODO = Scariness.SCARY;
    public static final Hardness UPDATED_HARDNESS_TODO = Hardness.HARD;
    public static final int UPDATED_TODO_ORDER_NUMBER = 222;
    public static final String UPDATED_COLOR_TODO = "6e6e6e";

    private DayTodoFactory() {
    }

    public static DayTodoRequestDto createDayTodoRequestDto() {
        return DayTodoRequestDto
            .builder()
            .orderNumber(TODO_ORDER_NUMBER)
            .dayTodoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .color(COLOR_TODO)
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
            .orderNumber(TODO_ORDER_NUMBER)
            .dayTodoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .day(day)
            .ready(FALSE_TODO_READY)
            .color(COLOR_TODO)
            .build();
    }

    public static DayTodo dayTodoWithDayReadyInstance(Day day) {
        return DayTodo
            .builder()
            .orderNumber(TODO_ORDER_NUMBER)
            .dayTodoName(TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .day(day)
            .ready(TRUE_TODO_READY)
            .color(COLOR_TODO)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestDto(Day savedDay) {
        return DayTodoRequestDto.builder()
            .orderNumber(UPDATED_TODO_ORDER_NUMBER)
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(FALSE_TODO_READY)
            .color(UPDATED_COLOR_TODO)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestReadyDto() {
        return DayTodoRequestDto.builder()
            .orderNumber(UPDATED_TODO_ORDER_NUMBER)
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .color(UPDATED_COLOR_TODO)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestReadyDtoWithChangedScariness() {
        return DayTodoRequestDto.builder()
            .orderNumber(UPDATED_TODO_ORDER_NUMBER)
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(UPDATED_SCARINESS_TODO)
            .hardness(HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .color(UPDATED_COLOR_TODO)
            .build();
    }

    public static DayTodoRequestDto updateDayTodoRequestReadyDtoWithChangedHardness() {
        return DayTodoRequestDto.builder()
            .orderNumber(UPDATED_TODO_ORDER_NUMBER)
            .dayTodoName(UPDATED_TODO_NAME)
            .scariness(SCARINESS_TODO)
            .hardness(UPDATED_HARDNESS_TODO)
            .ready(TRUE_TODO_READY)
            .color(UPDATED_COLOR_TODO)
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

}
