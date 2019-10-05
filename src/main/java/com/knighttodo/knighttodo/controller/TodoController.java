package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todoController")
public class TodoController {

    final private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/hello")
    public String getHello() {
        return "Hello!";
    }

    @GetMapping("/list")
    public ResponseEntity<List<Todo>> findAll(){
        List<Todo> todos = todoService.findAll();

        if (todos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            List<Todo> todosAfterCheck = new ArrayList<>(todos);

            return new ResponseEntity<>(todosAfterCheck, HttpStatus.FOUND);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<Todo> addCategory(@RequestBody Todo todo){
        todoService.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }
}
