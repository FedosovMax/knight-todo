package com.knighttodo.knighttodo.gateway.privatedb.representation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

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
