package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.service.TodoService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ready")
@Slf4j
public class IsReadyController {

    private TodoService todoService;

    @PutMapping("/update")
    public ResponseEntity<Todo> makeReady(@Valid @RequestBody Todo todo){
        log.info("Rest request to make todo : {} ready", todo);
        return new ResponseEntity<>(this.todoService.updateTodo(todo), HttpStatus.OK);
    }

}


