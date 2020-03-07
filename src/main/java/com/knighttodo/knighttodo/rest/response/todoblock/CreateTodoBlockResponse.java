package com.knighttodo.knighttodo.rest.response.todoblock;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoBlockResponse {

    private long id;

    private String blockName;

    private List<Todo> todoList;
}
