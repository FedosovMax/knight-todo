package com.knighttodo.knighttodo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "todo")
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "todo_name")
    private String todoName;

    @ManyToOne
    @JoinColumn(name = "todoBlock_id")
    @JsonBackReference
    private TodoBlock todoBlock;

}
