package com.knighttodo.todocore.character.rest.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private List<String> bonusIds = new ArrayList<>();
}
