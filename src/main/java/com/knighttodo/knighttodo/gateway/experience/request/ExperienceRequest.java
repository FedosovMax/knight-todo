package com.knighttodo.knighttodo.gateway.experience.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceRequest {

    private String todoId;

    private String userId;

    private String scariness;

    private String hardness;
}
