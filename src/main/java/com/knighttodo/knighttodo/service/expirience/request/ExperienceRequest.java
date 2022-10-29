package com.knighttodo.knighttodo.service.expirience.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceRequest {

    private UUID todoId;

    private UUID userId;

    private String scariness;

    private String hardness;
}
