package com.knighttodo.knighttodo.rest.dto.todo.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
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
public class UpdateTodoRequestDto {

    @NotBlank
    private String todoName;

    @NotNull
    private Scaryness scaryness;

    @NotNull
    private Hardness hardness;

    private boolean ready;
}
