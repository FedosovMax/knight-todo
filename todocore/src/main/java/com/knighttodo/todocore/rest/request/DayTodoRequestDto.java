package com.knighttodo.todocore.rest.request;

import com.knighttodo.todocore.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayTodoRequestDto {

    private int orderNumber;

    @NotBlank
    private String dayTodoName;

    @NotNull
    private Scariness scariness;

    @NotNull
    private Hardness hardness;

    private String color;

    private boolean ready;
}
