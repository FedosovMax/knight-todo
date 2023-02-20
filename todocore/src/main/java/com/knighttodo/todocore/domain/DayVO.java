package com.knighttodo.todocore.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayVO {

    private UUID id;

    private String dayName;

    private LocalDate date;

    private List<DayTodoVO> dayTodos = new ArrayList<>();
}
