package com.knighttodo.knighttodo.service.expirience.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExperienceResponse {

    private UUID todoId;
    private int experience;
}
