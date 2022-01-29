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
@ToString
public class RoutineTodoInstance {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "routine_todo_instance_name")
    private String routineTodoInstanceName;

    @Column(name = "scaryness")
    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Column(name = "hardness")
    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    @Column(name = "ready")
    private boolean ready = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoutineInstance routineInstance;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoutineTodo routineTodo;
}
