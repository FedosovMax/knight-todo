package com.knighttodo.todocore.service.privatedb.representation;

import com.knighttodo.todocore.service.privatedb.representation.enums.Hardness;
import com.knighttodo.todocore.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "day_todo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"day"})
public class DayTodo {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "day_todo_name")
    private String dayTodoName;

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

    @ManyToOne
    private Day day;
}