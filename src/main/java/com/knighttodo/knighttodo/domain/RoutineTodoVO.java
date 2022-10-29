package com.knighttodo.knighttodo.domain;

import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "routineTodoInstances")
public class RoutineTodoVO implements TodoVO {

    private UUID id;

    private String routineTodoName;

    private Scariness scariness;

    private Hardness hardness;

    private boolean ready;

    private RoutineVO routineVO;

    private List<RoutineTodoInstanceVO> routineTodoInstances;

    private int experience;
}
