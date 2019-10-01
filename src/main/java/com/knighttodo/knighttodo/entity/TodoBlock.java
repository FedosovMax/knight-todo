package com.knighttodo.knighttodo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todoBlock")
@Data
public class TodoBlock {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "block_name")
    private String blockName;

    @OneToMany(mappedBy = "todo")
    @JsonManagedReference
    private List<Todo> todoList = new ArrayList<>();
}
