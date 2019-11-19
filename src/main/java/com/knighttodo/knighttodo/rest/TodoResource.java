package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Todo;
import com.knighttodo.knighttodo.rest.request.TodoRequest;
import com.knighttodo.knighttodo.rest.response.TodoResponse;
import com.knighttodo.knighttodo.service.TodoService;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("todos")
@Slf4j
public class TodoResource {

    private final TodoService todoService;
    private final TodoMapper todoMapper;

    @GetMapping("/todo")
    public ResponseEntity<List<TodoResponse>> findAll() {
        log.info("Rest request to get all todo");

        List<TodoVO> todoVOS = todoService.findAll();
        List<TodoResponse> todoResponses = new ArrayList<>();

        if (todoVOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<TodoVO> todosResponseAfterCheck = new ArrayList<>(todoVOS);
            for (TodoVO todoVO : todosResponseAfterCheck) {
                todoResponses.add(todoMapper.todoResponseFromTodoVO(todoVO));
            }

            return new ResponseEntity<>(todoResponses, HttpStatus.FOUND);
        }
    }

    @PostMapping("/todo")
    public ResponseEntity<TodoResponse> addTodo(@RequestBody TodoRequest todoRequest) {
        log.info("Rest request to add todo : {}", todoRequest);

        TodoVO todoVO = todoMapper.toTodoVO(todoRequest);
        todoService.save(todoVO);
        TodoResponse todoResponse = todoMapper.todoResponseFromTodoVO(todoVO);

        return new ResponseEntity<>(todoResponse, HttpStatus.CREATED);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable long todoId) {
        log.info("Rest request to get todo by id : {}", todoId);

        TodoResponse todoResponse = todoMapper.todoResponseFromTodoVO(todoService.findById(todoId));

        if (todoResponse == null) {
            throw new RuntimeException("Todo id not found - " + todoId);
        }
        return new ResponseEntity<>(todoResponse, HttpStatus.FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<TodoResponse> updateTodo(@Valid @RequestBody TodoRequest todoRequest) {
        log.info("Rest request to update todo : {}", todoRequest);

        TodoVO todoVO = todoMapper.toTodoVO(todoRequest);
        todoService.updateTodo(todoVO);
        TodoResponse todoResponse = todoMapper.todoResponseFromTodoVO(todoVO);

        return new ResponseEntity<>(todoResponse, HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable long todoId) {
        log.info("Rest request to delete todo by id : {}", todoId);

        todoService.deleteById(todoId);

        return new ResponseEntity<>("Deleted Todo id " + todoId, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<TodoResponse> makeReady(@Valid @RequestBody TodoRequest todoRequest) {
        log.info("Rest request to make todo : {} ready", todoRequest);

        TodoVO todoVO = todoMapper.toTodoVO(todoRequest);
        todoService.updateTodo(todoVO);
        TodoResponse todoResponse = todoMapper.todoResponseFromTodoVO(todoVO);

        return new ResponseEntity<>(todoResponse, HttpStatus.OK);
    }

    // MICROSERVICES

    // send request to TodoBlock

    @GetMapping("/getAllTodo/{blockId}")
    public ResponseEntity<List<TodoResponse>> getAllTodoByBlockId(@PathVariable long blockId) {
        log.info("request for TodoBlock to get all todo by todoBlock id");

        List<TodoVO> todosVO = todoService.getAllTodoByBlockId(blockId);
        List<TodoResponse> todosResponses = new ArrayList<>();

        for (TodoVO todoVO : todosVO) {
            todosResponses.add(todoMapper.todoResponseFromTodoVO(todoVO));
        }

        return new ResponseEntity<>(todosResponses, HttpStatus.FOUND);
    }

}
