package com.knighttodo.todocore.domain;

import com.knighttodo.todocore.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoutineTodoInstanceVO implements TodoVO {

    private UUID id;

    private String routineTodoInstanceName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private RoutineInstanceVO routineInstanceVO;

    private RoutineTodoVO routineTodoVO;

    private int experience;
}
