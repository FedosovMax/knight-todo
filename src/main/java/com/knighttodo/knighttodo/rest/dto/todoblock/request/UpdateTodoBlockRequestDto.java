package com.knighttodo.knighttodo.rest.dto.todoblock.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTodoBlockRequestDto {

    @Min(1)
    private long id;

    @NotBlank
    private String blockName;

    @NotNull
    private List<Todo> todos;
}
