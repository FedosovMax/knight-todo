package com.knighttodo.knighttodo.rest.dto.todoblock.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTodoBlockRequestDto {

    @NotBlank
    private String blockName;
}
