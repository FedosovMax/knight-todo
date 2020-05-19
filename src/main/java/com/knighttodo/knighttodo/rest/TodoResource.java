package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.Constants.BASE_READY;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.request.TodoRequestDto;
import com.knighttodo.knighttodo.rest.response.TodoResponseDto;
import com.knighttodo.knighttodo.rest.response.TodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.mapper.TodoRestMapper;
import com.knighttodo.knighttodo.service.TodoService;

import io.swagger.annotations.ApiOperation;
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
public class TodoResource {

    private final TodoService todoService;
    private final TodoRestMapper todoRestMapper;

    @PostMapping
    @ApiOperation(value = "Add new Todo")
    public ResponseEntity<TodoResponseDto> addTodo(@PathVariable String blockId,
        @Valid @RequestBody TodoRequestDto requestDto) {
        log.info("Rest request to add todo : {}", requestDto);
        TodoVO todoVO = todoRestMapper.toTodoVO(requestDto);
        TodoVO savedTodoVO = todoService.save(blockId, todoVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(todoRestMapper.toTodoResponseDto(savedTodoVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Todos by the block id")
    public ResponseEntity<List<TodoResponseDto>> findTodosByBlockId(@PathVariable String blockId) {
        log.info("request for block to get all todos by block id");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoService.findByBlockId(blockId)
                .stream()
                .map(todoRestMapper::toTodoResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{todoId}")
    @ApiOperation(value = "Find the Todo by id")
    public ResponseEntity<TodoReadyResponseDto> findTodoById(@PathVariable String todoId) {
        log.info("Rest request to get todo by id : {}", todoId);
        TodoVO todoVO = todoService.findById(todoId);

        return ResponseEntity.status(HttpStatus.FOUND).body(todoRestMapper.toTodoReadyResponseDto(todoVO));
    }

    @PutMapping("/{todoId}")
    @ApiOperation(value = "Update the Todo by id")
    public ResponseEntity<TodoResponseDto> updateTodo(@PathVariable String todoId,
        @Valid @RequestBody TodoRequestDto requestDto) {
        log.info("Rest request to update todo : {}", requestDto);
        TodoVO todoVO = todoRestMapper.toTodoVO(requestDto);
        TodoVO updatedTodoVO = todoService.updateTodo(todoId, todoVO);
        return ResponseEntity.ok().body(todoRestMapper.toTodoResponseDto(updatedTodoVO));
    }

    @DeleteMapping("/{todoId}")
    @ApiOperation(value = "Delete the Todo by id")
    public ResponseEntity<Void> deleteTodo(@PathVariable String todoId) {
        log.info("Rest request to delete todo by id : {}", todoId);
        todoService.deleteById(todoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{todoId}" + BASE_READY)
    @ApiOperation(value = "Update an isReady field")
    public ResponseEntity<TodoReadyResponseDto> updateIsReady(@PathVariable String blockId, @PathVariable String todoId,
        @RequestParam String ready) {
        log.info("request to update isReady field of todo with id : {}, to value : {}", todoId, ready);
        boolean isReady = Boolean.parseBoolean(ready);
        TodoVO todoVO = todoService.updateIsReady(blockId, todoId, isReady);
        return ResponseEntity.status(HttpStatus.OK).body(todoRestMapper.toTodoReadyResponseDto(todoVO));
    }
}
