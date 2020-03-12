package com.knighttodo.knighttodo.rest.dto.request.todo;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
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
public class CreateTodoRequestDto {

    @NotBlank
    private String todoName;

    @NotNull
    private Scaryness scaryness;

    @NotNull
    private Hardness hardness;

    private boolean ready;

    @NotNull
    private TodoBlock todoBlock;
}
