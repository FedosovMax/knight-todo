package com.knighttodo.todocore.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String id;
    private String login;
    private String password;
    private List<RoleResponseDto> roles;
}
