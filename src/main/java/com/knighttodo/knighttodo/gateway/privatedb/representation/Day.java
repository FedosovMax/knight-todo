package com.knighttodo.knighttodo.gateway.privatedb.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "removed")
    private boolean removed;



    @OneToMany(mappedBy = "day", cascade = CascadeType.MERGE)
    private List<DayTodo> dayTodos = new ArrayList<>();
}
