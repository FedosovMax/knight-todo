package com.knighttodo.todocore.character.domain;

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
public class SkillVO {

    private String id;

    private String name;

    private String description;

    private List<BonusVO> bonuses = new ArrayList<>();
}
