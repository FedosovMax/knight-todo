package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("todos")
@Slf4j

public class TodoResource {

    final private TodoService todoService;

    private final RestTemplate rest;

    @Autowired
    public TodoResource(TodoService todoService, RestTemplateBuilder restTemplateBuilder) {
        this.todoService = todoService;
        this.rest = restTemplateBuilder.build();
    }


    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> findAll() {
        log.info("Rest request to get all todo");

        List<Todo> todos = todoService.findAll();

        if (todos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<Todo> todosAfterCheck = new ArrayList<>(todos);

            return new ResponseEntity<>(todosAfterCheck, HttpStatus.FOUND);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        log.info("Rest request to add todo : {}", todo);
        todoService.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable long todoId) {
        log.info("Rest request to get todo by id : {}", todoId);
        Todo todo = todoService.findById(todoId);

        if (todo == null) {
            throw new RuntimeException("Todo id not found - " + todoId);
        }
        return new ResponseEntity<>(todo, HttpStatus.FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> updateTodo(@Valid @RequestBody Todo todo) {
        log.info("Rest request to update todo : {}", todo);
        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable long todoId) {
        log.info("Rest request to delete todo by id : {}", todoId);
        todoService.deleteById(todoId);

        return new ResponseEntity<>("Deleted Todo id " + todoId, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Todo> makeReady(@Valid @RequestBody Todo todo) {
        log.info("Rest request to make todo : {} ready", todo);
        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

    // MICROSERVICES

    // send request to TodoBlock

    @GetMapping("/getAllTodo/{blockId}")
    public ResponseEntity<List<Todo>> getAllTodoByBlockId(@PathVariable long blockId) {
        log.info("request for TodoBlock to get all todo by todoBlock id");

        List<Todo> todos = todoService.getAllTodoByBlockId(blockId);

        return new ResponseEntity<>(todos, HttpStatus.FOUND);

    }

    @GetMapping("/getAllString")
    public ResponseEntity<List<String>> getAllTodoString(){

        List<String> strings = todoService.getAllStringTodo();

        return new ResponseEntity<>(strings, HttpStatus.OK);
    }
}
