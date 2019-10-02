package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
