package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routine_todo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"routineTodoInstances"})
public class RoutineTodo {

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

    @Column(name = "removed")
    private boolean removed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;

    @OneToMany(mappedBy = "routineTodo", cascade =  {CascadeType.MERGE})
    private List<RoutineTodoInstance> routineTodoInstances = new ArrayList<>();
}
