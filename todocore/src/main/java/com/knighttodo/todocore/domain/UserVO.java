package com.knighttodo.todocore.domain;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UserVO {

    private UUID id;
    private String login;
    private String password;
    private List<RoleVO> roles;
}
