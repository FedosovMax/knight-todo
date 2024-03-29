package com.knighttodo.todocore.rest.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDto {

    private String username;
    private String token;
}
