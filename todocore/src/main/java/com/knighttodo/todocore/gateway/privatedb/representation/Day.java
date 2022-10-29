package com.knighttodo.todocore.gateway.privatedb.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "day")
@Getter
@Setter
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
