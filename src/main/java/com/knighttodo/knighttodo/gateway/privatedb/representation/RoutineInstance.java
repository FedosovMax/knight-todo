package com.knighttodo.knighttodo.gateway.privatedb.representation;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routine_instance")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "routine")
public class RoutineInstance extends Routine {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private boolean ready;

    @ManyToOne(cascade = CascadeType.ALL)
    private Routine routine;

    @OneToMany(mappedBy = "routineInstance", cascade = CascadeType.ALL)
    private List<RoutineTodo> routineTodos;
}
