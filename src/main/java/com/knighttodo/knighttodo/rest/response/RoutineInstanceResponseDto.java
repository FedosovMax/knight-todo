package com.knighttodo.knighttodo.rest.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineInstanceResponseDto {

    private UUID id;

    private String name;

    private Hardness hardness;

    private Scariness scariness;

    private boolean ready;

    private List<RoutineTodoResponseDto> routineTodos = new ArrayList<>();
}
