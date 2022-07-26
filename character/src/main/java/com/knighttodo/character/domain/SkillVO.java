package com.knighttodo.character.domain;

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
public class SkillVO {

    private String id;

    private String name;

    private String description;

    private List<BonusVO> bonuses = new ArrayList<>();
}
