package com.knighttodo.knighttodo.gateway.experience.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {

    private long id;

    private Long userId;

    private String scaryness;

    private String hardness;

    private Long experience;
}
