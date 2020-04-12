package com.knighttodo.knighttodo.gateway.experience.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceResponse {

    private String todoId;
    private Integer experience;
}
