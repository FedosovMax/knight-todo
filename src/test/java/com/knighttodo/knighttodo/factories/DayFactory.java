package com.knighttodo.knighttodo.factories;

import com.knighttodo.knighttodo.service.privatedb.representation.Day;
import com.knighttodo.knighttodo.rest.request.DayRequestDto;

import java.util.ArrayList;

public class DayFactory {

    public static final String DAY_NAME = "Sunday Todos";
    public static final String UPDATED_DAY_NAME = "Friday Todos";

    public static DayRequestDto createDayRequestDto() {
        return DayRequestDto
            .builder()
            .dayName(DAY_NAME)
            .build();
    }

    public static DayRequestDto createDayRequestDtoWithoutName() {
        DayRequestDto request = createDayRequestDto();
        request.setDayName(null);
        return request;
    }

    public static DayRequestDto createDayRequestDtoWithNameConsistingOfSpaces() {
        DayRequestDto request = createDayRequestDto();
        request.setDayName("    ");
        return request;
    }

    public static Day dayInstance() {
        return Day
            .builder()
            .dayName(DAY_NAME)
            .dayTodos(new ArrayList<>())
            .build();
    }

    public static DayRequestDto updateDayRequestDto() {
        return DayRequestDto
            .builder()
            .dayName(UPDATED_DAY_NAME)
            .build();
    }

    public static DayRequestDto updateDayRequestDtoWithoutName() {
        DayRequestDto request = updateDayRequestDto();
        request.setDayName(null);
        return request;
    }

    public static DayRequestDto updateDayRequestDtoWithNameConsistingOfSpaces() {
        DayRequestDto request = updateDayRequestDto();
        request.setDayName("    ");
        return request;
    }
}
