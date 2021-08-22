package com.knighttodo.knighttodo.rest.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineTodoInstanceReadyResponseDto {

    private UUID id;

    private String routineTodoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private UUID routineId;

    private int experience;
}
