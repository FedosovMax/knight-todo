package com.knighttodo.knighttodo.rest.dto.todo.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class DeleteTodoRequestDto {

    @Min(1)
    private long id;
}
