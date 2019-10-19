package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todos")
public class TodoController {

    final private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todo")
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

    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo){
        todoService.save(todo);

        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<Todo> getTodoById(@PathVariable long todoId){
        Todo todo = todoService.findById(todoId);

        return new ResponseEntity<>(todo, HttpStatus.FOUND);
    }

    @PutMapping("/todo/")
    public ResponseEntity<Todo> updateTodo(@Valid @RequestBody Todo todo){

        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable long todoId){

        todoService.deleteById(todoId);

        return new ResponseEntity<>("Deleted Todo id " + todoId, HttpStatus.OK);
    }
}
