package com.knighttodo.knighttodo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "todoBlock")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "block_name")
    private String blockName;

    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "todoBlock")
    @JsonIgnore
    private List<Todo> todoList = new ArrayList<>();

    public void addTodo(final Todo todo) {
        todoList.add(todo);
        todo.setTodoBlock(this);
    }

    public void removeTodo(final Todo todo) {
        todoList.remove(todo);
        todo.setTodoBlock(null);
    }
}
