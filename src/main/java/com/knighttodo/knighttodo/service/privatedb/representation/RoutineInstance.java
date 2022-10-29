package com.knighttodo.knighttodo.service.privatedb.representation;

import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routine_instance")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"routineTodoInstances"})
public class RoutineInstance {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    @Column(name = "ready")
    private boolean ready;

    @Column(name = "removed")
    private boolean removed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;

    @OneToMany(mappedBy = "routineInstance", cascade =  {CascadeType.MERGE})
    private List<RoutineTodoInstance> routineTodoInstances = new ArrayList<>();
}
