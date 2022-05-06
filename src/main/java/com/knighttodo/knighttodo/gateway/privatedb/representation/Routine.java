package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routine")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"routineInstances", "routineTodos"})
public class Routine {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "scaryness")
    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Column(name = "hardness")
    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    @Column(name = "removed")
    private boolean removed;

    @OneToMany(mappedBy = "routine", cascade =  {CascadeType.MERGE})
    private List<RoutineInstance> routineInstances = new ArrayList<>();

    @OneToMany(mappedBy = "routine", cascade =  {CascadeType.MERGE})
    private List<RoutineTodo> routineTodos = new ArrayList<>();
}
