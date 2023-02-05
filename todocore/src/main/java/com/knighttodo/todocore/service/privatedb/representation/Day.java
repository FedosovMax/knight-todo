package com.knighttodo.todocore.service.privatedb.representation;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @CreationTimestamp
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "date")
    private Date date = new java.sql.Date(new java.util.Date().getTime());;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "removed")
    private boolean removed;

    @OneToMany(mappedBy = "day", cascade = CascadeType.MERGE)
    private List<DayTodo> dayTodos = new ArrayList<>();
}
