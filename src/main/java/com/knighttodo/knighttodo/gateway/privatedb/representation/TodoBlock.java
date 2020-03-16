package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;
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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "todo_block")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoBlock {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private String id;

    @Column(name = "block_name")
    private String blockName;

    @OneToMany(mappedBy = "todoBlock", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Todo> todos = new ArrayList<>();

    public void addTodo(final Todo todo) {
        todos.add(todo);
        todo.setTodoBlock(this);
    }

    public void removeTodo(Todo todo) {
        todos.remove(todo);
        todo.setTodoBlock(null);
    }
}
