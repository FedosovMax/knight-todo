package com.knighttodo.todocore.service.privatedb.representation;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
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

    @CreationTimestamp
    @Column(name = "created")
    private LocalDate created;

    @Column(name = "removed")
    private boolean removed;

    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;

    @OneToMany(mappedBy = "routineInstance", cascade =  {CascadeType.MERGE})
    private List<RoutineTodoInstance> routineTodoInstances = new ArrayList<>();
}
