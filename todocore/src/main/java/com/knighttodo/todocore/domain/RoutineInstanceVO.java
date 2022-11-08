package com.knighttodo.todocore.domain;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "routineTodoInstances")
public class RoutineInstanceVO {

    private UUID id;

    private String name;

    private Hardness hardness;

    private Scariness scariness;

    private boolean ready;

    private RoutineVO routine;

    private List<RoutineTodoInstanceVO> routineTodoInstances;
}
