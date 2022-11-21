package com.knighttodo.todocore.domain;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DayTodoVO implements TodoVO {

    private UUID id;

    private String dayTodoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private DayVO day;

    private String color;

    private int experience;
}
