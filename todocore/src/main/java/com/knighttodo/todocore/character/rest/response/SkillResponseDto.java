package com.knighttodo.todocore.character.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SkillResponseDto {

    private String id;

    private String name;

    private String description;

    private List<BonusResponseDto> bonuses = new ArrayList<>();
}
