package com.knighttodo.knighttodo.gateway.experience.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequest {

    private long id;

    private Long userId;

    private String scaryness;

    private String hardness;

    private Boolean isReady;
}
