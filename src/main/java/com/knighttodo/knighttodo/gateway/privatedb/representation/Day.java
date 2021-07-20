package com.knighttodo.knighttodo.gateway.privatedb.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "day")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Day {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
            name = "uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "day_name")
    private String dayName;

    @OneToMany(mappedBy = "day", cascade = CascadeType.ALL)
    private List<DayTodo> dayTodos = new ArrayList<>();
}
