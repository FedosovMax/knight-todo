package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.mapper.TodoRestMapper;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.CreateTodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.TodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.UpdateTodoResponseDto;
import com.knighttodo.knighttodo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;
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
    private final TodoRestMapper todoRestMapper;

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        log.info("Rest request to get all todo");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.findAll()
                      .stream()
                      .map(todoRestMapper::toTodoResponseDto)
                      .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CreateTodoResponseDto> addTodo(@Valid @RequestBody CreateTodoRequestDto createRequestDto) {
        log.info("Rest request to add todo : {}", createRequestDto);
        TodoVO todoVO = todoRestMapper.toTodoVO(createRequestDto);
        TodoVO savedTodoVO = todoService.save(todoVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(todoRestMapper.toCreateTodoResponseDto(savedTodoVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable long id) {
        log.info("Rest request to get todo by id : {}", id);
        TodoVO todoVO = todoService.findById(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(todoRestMapper.toTodoResponseDto(todoVO));
    }

    @PutMapping
    public ResponseEntity<UpdateTodoResponseDto> updateTodo(
        @Valid @RequestBody UpdateTodoRequestDto updateRequestDto) {
        log.info("Rest request to update todo : {}", updateRequestDto);
        TodoVO todoVO = todoRestMapper.toTodoVO(updateRequestDto);
        TodoVO updatedTodoVO = todoService.updateTodo(todoVO);

        return ResponseEntity.ok().body(todoRestMapper.toUpdateTodoResponseDto(updatedTodoVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable long id) {
        log.info("Rest request to delete todo by id : {}", id);
        todoService.deleteById(id);

        return ResponseEntity.ok().body("Deleted Todo id " + id);
    }

    @GetMapping("/byBlockId/{id}")
    public ResponseEntity<List<TodoResponseDto>> getTodosByBlockId(@PathVariable long id) {
        log.info("request for TodoBlock to get all todo by todoBlock id");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.findByBlockId(id)
                      .stream()
                      .map(todoRestMapper::toTodoResponseDto)
                      .collect(Collectors.toList()));
    }
}
