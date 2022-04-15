package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "routine_todo_instance")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"routineInstance"})
public class RoutineTodoInstance {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "routine_todo_name")
    private String routineTodoName;

    @Column(name = "scaryness")
    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Column(name = "hardness")
    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    @Column(name = "ready")
    private boolean ready = false;

    @ManyToOne
    private RoutineInstance routineInstance;

    @ManyToOne
    private RoutineTodo routineTodo;
}
