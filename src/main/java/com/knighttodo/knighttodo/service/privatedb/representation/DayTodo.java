package com.knighttodo.knighttodo.service.privatedb.representation;

import com.knighttodo.knighttodo.service.privatedb.representation.Day;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import lombok.*;

import javax.persistence.*;
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
