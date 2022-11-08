package com.knighttodo.todocore.rest.response;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private List<RoutineTodoInstanceResponseDto> routineTodoInstances;
}
