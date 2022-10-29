package com.knighttodo.todocore.character.rest.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExperienceRequestDto {

    @NotBlank
    private String todoId;

    @NotBlank
    private String userId;

    @NotBlank
    private String scariness;

    @NotBlank
    private String hardness;
}
