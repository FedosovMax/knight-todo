package com.knighttodo.knighttodo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private List<DayTodoVO> dayTodos = new ArrayList<>();
}
