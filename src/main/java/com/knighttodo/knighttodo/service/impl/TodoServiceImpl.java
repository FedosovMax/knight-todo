package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoGateway;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.service.TodoBlockService;
import com.knighttodo.knighttodo.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoGateway todoGateway;
    private final TodoBlockService todoBlockService;

    @Override
    public Todo save(Todo todo) {
        return todoGateway.save(todo); // need to add todoBlockId from requestEntity
    }

    @Override
    public List<Todo> findAll() {
        return todoGateway.findAll();
    }

    @Override
    public Todo findById(long todoId) {
        Optional<Todo> result = todoGateway.findById(todoId);

        Todo todo;

        if (result.isPresent()) {
            todo = result.get();
        } else {
            throw new RuntimeException("Did not find Todo id - " + todoId);
        }

        return todo;
    }

    @Override
    public Todo updateTodo(Todo changedTodo) {

        final Todo todo = this.todoGateway.findById(changedTodo.getId())
            .orElseThrow(TodoNotFoundException::new);

        todo.setId(changedTodo.getId());
        todo.setTodoName(changedTodo.getTodoName());
        todo.setTodoBlock(changedTodo.getTodoBlock());

        todoGateway.save(todo);

        return todo;
    }

    @Override
    public void deleteById(long todoId) {
        todoGateway.deleteById(todoId);
    }


    @Override
    public List<Todo> getAllTodoByBlockId(long blockId) {
        TodoBlock todoBlock = new TodoBlock();

        List<Todo> allTodos = todoGateway.findAll();
        List<Todo> todosForBlock = new ArrayList<>();

        for (Todo todo : allTodos) {
            if (todo.getTodoBlock().getId() == todoBlock.getId()) {
                todosForBlock.add(todo);
            }
        }

        return todosForBlock;
    }

}

