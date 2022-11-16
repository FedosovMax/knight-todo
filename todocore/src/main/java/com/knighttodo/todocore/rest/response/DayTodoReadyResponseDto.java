package com.knighttodo.todocore.rest.response;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayTodoReadyResponseDto {

    private UUID id;

    private int orderNumber;

    private String dayTodoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private UUID dayId;

    private int experience;
}
