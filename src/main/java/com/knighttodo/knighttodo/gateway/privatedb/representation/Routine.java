package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
@ToString
public class Routine {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<RoutineInstance> routineInstances = new ArrayList<>();

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL)
    private List<RoutineTodo> routineTodos;
}
