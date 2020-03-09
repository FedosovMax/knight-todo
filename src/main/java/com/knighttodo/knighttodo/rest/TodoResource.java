package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.mapper.TodoMapper;
import com.knighttodo.knighttodo.rest.request.todo.CreateTodoRequest;
import com.knighttodo.knighttodo.rest.request.todo.UpdateTodoRequest;
import com.knighttodo.knighttodo.rest.response.todo.CreateTodoResponse;
import com.knighttodo.knighttodo.rest.response.todo.TodoResponse;
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
    public ResponseEntity<List<TodoResponse>> findAll() {
        log.info("Rest request to get all todo");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.findAll()
                      .stream()
                      .map(todoMapper::toTodoResponse)
                      .collect(Collectors.toList()));
    }

    @PostMapping("/todo")
    public ResponseEntity<CreateTodoResponse> addTodo(@RequestBody CreateTodoRequest createRequest) {
        log.info("Rest request to add todo : {}", createRequest);
        TodoVO todoVO = todoMapper.toTodoVO(createRequest);

        TodoVO savedTodoVO = todoService.save(todoVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(todoMapper.toCreateTodoResponse(savedTodoVO));
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<TodoResponse> getTodoById(@PathVariable @Min(1) long todoId) {
        log.info("Rest request to get todo by id : {}", todoId);
        TodoVO todoVO = todoService.findById(todoId);

        return ResponseEntity.status(HttpStatus.FOUND).body(todoMapper.toTodoResponse(todoVO));
    }

    @PutMapping("/todo")
    public ResponseEntity<UpdateTodoResponse> updateTodo(@Valid @RequestBody UpdateTodoRequest updateRequest) {
        log.info("Rest request to update todo : {}", updateRequest);
        TodoVO todoVO = todoMapper.toTodoVO(updateRequest);

        TodoVO updatedTodoVO = todoService.updateTodo(todoVO);

        return ResponseEntity.ok().body(todoMapper.toUpdateTodoResponse(updatedTodoVO));
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable @Min(1) long todoId) {
        log.info("Rest request to delete todo by id : {}", todoId);
        todoService.deleteById(todoId);

        return ResponseEntity.ok().body("Deleted Todo id " + todoId);
    }

    @PutMapping("/update")
    public ResponseEntity<UpdateTodoResponse> makeReady(@Valid @RequestBody UpdateTodoRequest updateRequest) {
        log.info("Rest request to make todo : {} ready", updateRequest);
        TodoVO todoVO = todoMapper.toTodoVO(updateRequest);

        TodoVO updatedTodoVO = todoService.updateTodo(todoVO);

        return ResponseEntity.ok().body(todoMapper.toUpdateTodoResponse(updatedTodoVO));
    }

    @GetMapping("/getAllTodo/{blockId}")
    public ResponseEntity<List<TodoResponse>> getAllTodoByBlockId(@PathVariable @Min(1) long blockId) {
        log.info("request for TodoBlock to get all todo by todoBlock id");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.getAllTodoByBlockId(blockId)
                      .stream()
                      .map(todoMapper::toTodoResponse)
                      .collect(Collectors.toList()));
    }
}
