package com.knighttodo.knighttodo.rest.dto.routine.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import com.knighttodo.knighttodo.rest.dto.todo.response.TodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.TodoBlockResponseDto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineResponseDto {

    private String id;

    private String name;

    private Hardness hardness;

    private Scaryness scaryness;

    private boolean ready;

    private String parentTemplateId;

    private TodoBlockResponseDto todoBlock;

    private List<TodoResponseDto> todos = new ArrayList<>();
}
