package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/ready")
public class ItReadyController {

    private TodoService todoService;

    public ItReadyController(TodoService todoService){
        this.todoService = todoService;
    }

    @PutMapping("/update")
    public ResponseEntity<Todo> updateTodo(@Valid @RequestBody Todo todo){

        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

}


