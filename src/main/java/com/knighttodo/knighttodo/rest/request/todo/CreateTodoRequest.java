package com.knighttodo.knighttodo.rest.request.todo;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoRequest {

    @Min(1)
    private long id;

    @NotBlank
    private String todoName;

    @NotBlank
    private Scaryness scaryness;

    @NotBlank
    private Hardness hardness;

    @NotBlank
    private boolean ready;

    @NotBlank
    private TodoBlock todoBlock;
}
