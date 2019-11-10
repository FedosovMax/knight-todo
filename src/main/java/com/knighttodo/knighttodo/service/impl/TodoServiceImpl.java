package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoRepository;
import com.knighttodo.knighttodo.service.TodoService;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class TodoServiceImpl implements TodoService {

    final private TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void save(Todo todo) {
        todoRepository.save(todo);
        TodoBlock todoBlock = new TodoBlock();
        todoBlock.addTodo(todo);
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Todo findById(long todoId) {
        Optional<Todo> result = todoRepository.findById(todoId);

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

        final Todo todo = this.todoRepository.findById(changedTodo.getId())
            .orElseThrow(TodoNotFoundException::new);

        todo.setId(changedTodo.getId());
        todo.setTodoName(changedTodo.getTodoName());
        todo.setTodoBlock(changedTodo.getTodoBlock());

        todoRepository.save(todo);

        return todo;
    }

    @Override
    public void deleteById(long todoId) {
        todoRepository.deleteById(todoId);
    }
}

