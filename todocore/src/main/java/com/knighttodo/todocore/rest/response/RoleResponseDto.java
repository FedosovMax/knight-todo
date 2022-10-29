package com.knighttodo.todocore.rest.response;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleResponseDto {

    private UUID id;
    private String name;
}
