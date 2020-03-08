package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.mapper.TodoMapper;
import com.knighttodo.knighttodo.rest.request.todo.CreateTodoRequest;
import com.knighttodo.knighttodo.rest.request.todo.UpdateTodoRequest;
import com.knighttodo.knighttodo.rest.response.todo.CreateTodoResponse;
import com.knighttodo.knighttodo.rest.response.todo.GetTodoResponse;
import com.knighttodo.knighttodo.rest.response.todo.UpdateTodoResponse;
import com.knighttodo.knighttodo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;

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
    public ResponseEntity<List<GetTodoResponse>> findAll() {
        log.info("Rest request to get all todo");

        List<GetTodoResponse> response = todoService.findAll()
            .stream()
            .map(todoMapper::toGetTodoResponse)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping("/todo")
    public ResponseEntity<CreateTodoResponse> addTodo(@RequestBody CreateTodoRequest createRequest) {
        log.info("Rest request to add todo : {}", createRequest);
        TodoVO todoVO = todoMapper.toTodoVO(createRequest);

        TodoVO savedTodoVO = todoService.save(todoVO);
        CreateTodoResponse response = todoMapper.toCreateTodoResponse(savedTodoVO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<GetTodoResponse> getTodoById(@PathVariable @Min(1) long todoId) {
        log.info("Rest request to get todo by id : {}", todoId);
        TodoVO todoVO = todoService.findById(todoId);

        GetTodoResponse response = todoMapper.toGetTodoResponse(todoVO);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PutMapping("/todo")
    public ResponseEntity<UpdateTodoResponse> updateTodo(@Valid @RequestBody UpdateTodoRequest updateRequest) {
        log.info("Rest request to update todo : {}", updateRequest);
        TodoVO todoVO = todoMapper.toTodoVO(updateRequest);

        todoService.updateTodo(todoVO);
        UpdateTodoResponse response = todoMapper.toUpdateTodoResponse(todoVO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable @Min(1) long todoId) {
        log.info("Rest request to delete todo by id : {}", todoId);

        todoService.deleteById(todoId);

        return new ResponseEntity<>("Deleted Todo id " + todoId, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateTodoResponse> makeReady(@Valid @RequestBody UpdateTodoRequest updateRequest) {
        log.info("Rest request to make todo : {} ready", updateRequest);
        TodoVO todoVO = todoMapper.toTodoVO(updateRequest);

        TodoVO updatedTodoVO = todoService.updateTodo(todoVO);
        UpdateTodoResponse response = todoMapper.toUpdateTodoResponse(updatedTodoVO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllTodo/{blockId}")
    public ResponseEntity<List<GetTodoResponse>> getAllTodoByBlockId(@PathVariable @Min(1) long blockId) {
        log.info("request for TodoBlock to get all todo by todoBlock id");

        List<GetTodoResponse> response = todoService.getAllTodoByBlockId(blockId)
            .stream()
            .map(todoMapper::toGetTodoResponse)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
