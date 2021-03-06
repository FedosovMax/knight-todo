package com.knighttodo.knighttodo.rest.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;

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

    private Scariness scariness;

    private boolean ready;

    private String templateId;

    private String blockId;

    private List<TodoResponseDto> todos = new ArrayList<>();
}
