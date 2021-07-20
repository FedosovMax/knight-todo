package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
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

    @ManyToOne
    @JoinColumn(name = "day_id")
    private Day day;
}
