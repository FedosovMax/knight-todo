package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.Constants.API_GET_TODOS_BY_BLOCK_ID;
import static com.knighttodo.knighttodo.Constants.BASE_READY;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.CreateTodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.TodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.UpdateTodoResponseDto;
import com.knighttodo.knighttodo.rest.mapper.TodoRestMapper;
import com.knighttodo.knighttodo.service.TodoService;
import com.knighttodo.knighttodo.validation.annotation.ValidReady;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_BLOCKS + "/{blockId}" + API_BASE_TODOS)
@Slf4j
public class  TodoResource {

    private final TodoService todoService;
    private final TodoRestMapper todoRestMapper;

    @GetMapping
    public ResponseEntity<List<TodoResponseDto>> getAllTodos() {
        log.info("Rest request to get all todo");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.findAll().stream().map(todoRestMapper::toTodoResponseDto).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CreateTodoResponseDto> addTodo(@PathVariable String blockId,
        @Valid @RequestBody CreateTodoRequestDto createRequestDto) {
        log.info("Rest request to add todo : {}", createRequestDto);
        TodoVO todoVO = todoRestMapper.toTodoVO(createRequestDto);
        TodoVO savedTodoVO = todoService.save(blockId, todoVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(todoRestMapper.toCreateTodoResponseDto(savedTodoVO));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<TodoResponseDto> getTodoById(@PathVariable String todoId) {
        log.info("Rest request to get todo by id : {}", todoId);
        TodoVO todoVO = todoService.findById(todoId);

        return ResponseEntity.status(HttpStatus.FOUND).body(todoRestMapper.toTodoResponseDto(todoVO));
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<UpdateTodoResponseDto> updateTodo(@PathVariable String todoId,
        @Valid @RequestBody UpdateTodoRequestDto updateRequestDto) {
        log.info("Rest request to update todo : {}", updateRequestDto);
        TodoVO todoVO = todoRestMapper.toTodoVO(updateRequestDto);
        TodoVO updatedTodoVO = todoService.updateTodo(todoId, todoVO);
        return ResponseEntity.ok().body(todoRestMapper.toUpdateTodoResponseDto(updatedTodoVO));
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String todoId) {
        log.info("Rest request to delete todo by id : {}", todoId);
        todoService.deleteById(todoId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(API_GET_TODOS_BY_BLOCK_ID)
    public ResponseEntity<List<TodoResponseDto>> getTodosByBlockId(@PathVariable String blockId) {
        log.info("request for TodoBlock to get all todo by todoBlock id");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.findByBlockId(blockId)
                .stream()
                .map(todoRestMapper::toTodoResponseDto)
                .collect(Collectors.toList()));
    }

    @PutMapping(value = "/{todoId}" + BASE_READY)
    public ResponseEntity<TodoResponseDto> updateIsReady(@PathVariable String blockId, @PathVariable String todoId,
        @RequestParam String ready) {
        boolean isReady = Boolean.parseBoolean(ready);
        TodoVO todoVO = todoService.updateIsReady(blockId, todoId, isReady);
        return ResponseEntity.status(HttpStatus.OK).body(todoRestMapper.toTodoResponseDto(todoVO));
    }
}
