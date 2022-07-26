package com.knighttodo.character.rest.response;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
