package com.knighttodo.knighttodo.rest.dto.request.todo;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class GetTodoRequestDto {

    @Min(1)
    private long id;
}
