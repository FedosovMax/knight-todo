package com.knighttodo.character.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExperienceVO {

    private String todoId;

    private String userId;

    private Integer experience;

    private String scariness;

    private String hardness;
}
