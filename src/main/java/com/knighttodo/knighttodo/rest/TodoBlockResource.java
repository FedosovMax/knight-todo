package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.rest.mapper.TodoBlockRestMapper;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.CreateTodoBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.TodoBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.UpdateTodoBlockResponseDto;
import com.knighttodo.knighttodo.service.TodoBlockService;

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

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_BLOCKS)
@Slf4j
public class TodoBlockResource {

    private final TodoBlockService todoBlockService;
    private final TodoBlockRestMapper todoBlockRestMapper;

    @GetMapping
    public ResponseEntity<List<TodoBlockResponseDto>> getAllTodoBlocks() {
        log.info("Rest request to get all todo blocks");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoBlockService.findAll()
                      .stream()
                      .map(todoBlockRestMapper::toTodoBlockResponseDto)
                      .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CreateTodoBlockResponseDto> addTodoBlock(
        @Valid @RequestBody CreateTodoBlockRequestDto createRequestDto) {
        log.info("Rest request to add todoBlock : {}", createRequestDto);
        TodoBlockVO todoBlockVO = todoBlockRestMapper.toTodoBlockVO(createRequestDto);
        TodoBlockVO savedTodoBlockVO = todoBlockService.save(todoBlockVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(todoBlockRestMapper.toCreateTodoBlockResponseDto(savedTodoBlockVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoBlockResponseDto> getTodoBlockById(@PathVariable long id) {
        log.info("Rest request to get todoBlock by id : {}", id);
        TodoBlockVO todoBlockVO = todoBlockService.findById(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(todoBlockRestMapper.toTodoBlockResponseDto(todoBlockVO));
    }

    @PutMapping
    public ResponseEntity<UpdateTodoBlockResponseDto> updateTodoBlock(
        @Valid @RequestBody UpdateTodoBlockRequestDto updateRequestDto) {
        log.info("Rest request to update todo block : {}", updateRequestDto);
        TodoBlockVO todoBlockVO = todoBlockRestMapper.toTodoBlockVO(updateRequestDto);
        TodoBlockVO updatedTodoBlockVO = todoBlockService.updateTodoBlock(todoBlockVO);

        return ResponseEntity.ok().body(todoBlockRestMapper.toUpdateTodoBlockResponseDto(updatedTodoBlockVO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodoBlock(@PathVariable long id) {
        log.info("Rest request to delete todoBlock by id : {}", id);
        todoBlockService.deleteById(id);

        return ResponseEntity.ok().body("Deleted TodoBlock id " + id);
    }
}
