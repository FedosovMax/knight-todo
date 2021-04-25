package com.knighttodo.knighttodo.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayVO {

    private String id;

    private String dayName;

    private List<DayTodoVO> dayTodos = new ArrayList<>();
}