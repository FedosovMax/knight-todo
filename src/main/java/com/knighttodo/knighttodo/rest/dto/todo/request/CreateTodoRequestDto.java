package com.knighttodo.knighttodo.rest.dto.todo.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoRequestDto {

    @NotBlank
    private String todoName;

    @NotNull
    private Scariness scariness;

    @NotNull
    private Hardness hardness;

    private Boolean isReady;
}
