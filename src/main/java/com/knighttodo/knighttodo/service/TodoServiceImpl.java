package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.entity.exeptions.TodoNotFoundException;
import com.knighttodo.knighttodo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

        if (result.isPresent()){
            todo = result.get();
        }else {
            throw new RuntimeException("Did not find Todo id - " + todoId );
        }

        return todo;
    }

    @Override
    public Todo updateTodo(Todo changedTodo) {

        final Todo todo = this.todoRepository.findById(changedTodo.getId()).
                orElseThrow(TodoNotFoundException::new);

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

