package com.knighttodo.todocore.gateway.privatedb.representation;

import com.knighttodo.todocore.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
