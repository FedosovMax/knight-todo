package com.knighttodo.todocore.gateway.privatedb.representation;

import com.knighttodo.todocore.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import java.time.LocalDate;
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

    @CreationTimestamp
    @Column(name = "createdDate")
    private LocalDate createdDate;

    @Column(name = "removed")
    private boolean removed;

    @OneToMany(mappedBy = "routine", cascade =  {CascadeType.MERGE})
    private List<RoutineInstance> routineInstances = new ArrayList<>();

    @OneToMany(mappedBy = "routine", cascade =  {CascadeType.MERGE})
    private List<RoutineTodo> routineTodos = new ArrayList<>();
}
