package com.knighttodo.knighttodo.rest.dto.request.todoblock;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoBlockRequestDto {

    @NotBlank
    private String blockName;

    @NotNull
    private List<Todo> todos;
}
