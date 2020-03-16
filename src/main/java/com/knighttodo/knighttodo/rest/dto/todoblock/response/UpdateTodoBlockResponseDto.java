package com.knighttodo.knighttodo.rest.dto.todoblock.response;

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
public class UpdateTodoBlockResponseDto {

    private String id;

    private String blockName;

    private List<Todo> todos;
}
