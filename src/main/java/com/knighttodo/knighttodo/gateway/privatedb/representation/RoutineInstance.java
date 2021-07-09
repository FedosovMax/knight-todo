package com.knighttodo.knighttodo.gateway.privatedb.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "routine_instance")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutineInstance extends Routine {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private boolean ready;

    @ManyToOne
    private Routine routine;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.REMOVE)
    private List<RoutineTodo> routineTodos = new ArrayList<>();
}
