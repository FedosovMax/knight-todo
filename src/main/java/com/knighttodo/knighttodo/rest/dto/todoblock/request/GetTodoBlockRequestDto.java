package com.knighttodo.knighttodo.rest.dto.todoblock.request;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class GetTodoBlockRequestDto {

    @Min(1)
    private long id;
}
