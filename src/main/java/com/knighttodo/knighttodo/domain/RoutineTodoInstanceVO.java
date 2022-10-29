package com.knighttodo.knighttodo.domain;

import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import lombok.*;

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
