package com.knighttodo.todocore.domain;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"routineInstanceVOs", "routineTodos"})
public class RoutineVO {

    private UUID id;

    private String name;

    private Scariness scariness;

    private Hardness hardness;

    private LocalDate creationDate;

    private List<RoutineInstanceVO> routineInstanceVOs;

    private List<RoutineTodoVO> routineTodos;
}
