package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("todos")
@Slf4j
public class TodoController {

    final private TodoService todoService;

    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> findAll(){
        log.info("Rest request to get all todo");
        List<Todo> todos = todoService.findAll();

        if (todos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            List<Todo> todosAfterCheck = new ArrayList<>(todos);

            return new ResponseEntity<>(todosAfterCheck, HttpStatus.FOUND);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo){
        log.info("Rest request to add todo : {}", todo );
        todoService.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable long todoId){
        log.info("Rest request to get todo by id : {}", todoId);
        Todo todo = todoService.findById(todoId);

        if (todo == null){
            throw new RuntimeException("Todo id not found - " + todoId);
        }
        return new ResponseEntity<>(todo, HttpStatus.FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> updateTodo(@Valid @RequestBody Todo todo){
        log.info("Rest request to update todo : {}", todo);
        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable long todoId){
        log.info("Rest request to delete todo by id : {}", todoId);
        todoService.deleteById(todoId);

        return new ResponseEntity<>("Deleted Todo id " + todoId, HttpStatus.OK);
    }
}
