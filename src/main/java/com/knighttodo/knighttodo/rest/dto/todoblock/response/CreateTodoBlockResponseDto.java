package com.knighttodo.knighttodo.rest.dto.todoblock.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoBlockResponseDto {

    private String id;

    private String blockName;
}
