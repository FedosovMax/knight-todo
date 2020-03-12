package com.knighttodo.knighttodo.rest.dto.request.todoblock;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class DeleteTodoBlockRequestDto {

    @Min(1)
    private long id;
}
