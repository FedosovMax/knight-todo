package com.knighttodo.knighttodo.rest.request.todoblock;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoBlockRequest {

    @Min(1)
    private long id;

    @NotBlank
    private String blockName;

    @NotBlank
    private List<Todo> todoList;
}
