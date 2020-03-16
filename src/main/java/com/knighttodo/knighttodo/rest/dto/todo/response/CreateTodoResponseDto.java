package com.knighttodo.knighttodo.rest.dto.todo.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoResponseDto {

    private String id;

    private String todoName;

    private Scaryness scaryness;

    private Hardness hardness;

    private boolean ready;

    private TodoBlock todoBlock;
}
