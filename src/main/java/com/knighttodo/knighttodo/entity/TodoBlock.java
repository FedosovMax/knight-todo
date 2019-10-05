package com.knighttodo.knighttodo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import junit.runner.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todoBlock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoBlock {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "block_name")
    private String blockName;

    @OneToMany(mappedBy = "todoBlock")
    @JsonManagedReference
    private List<Todo> todoList = new ArrayList<>();


    public void addTodo(final Todo todo){
		todoList.add(todo);
		todo.setTodoBlock(this);
	}
}
