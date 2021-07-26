package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routine_instance")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "routine")
public class RoutineInstance {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    private boolean ready;

    @ManyToOne
    private Routine routine;

    @OneToMany(mappedBy = "routineInstance", cascade = CascadeType.ALL)
    private List<RoutineTodoInstance> routineTodoInstances;
}
