package com.knighttodo.knighttodo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.knighttodo.knighttodo.entity.enums.Hardness;
import com.knighttodo.knighttodo.entity.enums.Scaryness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "todo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "todo_name")
    private String todoName;

    @Column(name = "scaryness")
    @Enumerated (EnumType.STRING)
    private Scaryness scaryness;

    @Column(name = "hardness")
    @Enumerated (EnumType.STRING)
    private Hardness hardness;

    @Column(name = "ready")
    private boolean ready;

    @ManyToOne
    @JoinColumn(name = "todoBlock_id")
    @JsonBackReference
    private TodoBlock todoBlock;
}
