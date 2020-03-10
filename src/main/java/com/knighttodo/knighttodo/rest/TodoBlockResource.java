package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.rest.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.rest.request.todoblock.CreateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.request.todoblock.UpdateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.response.todoblock.CreateTodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.TodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.UpdateTodoBlockResponse;
import com.knighttodo.knighttodo.service.TodoBlockService;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

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
@RequestMapping("blocks")
@Slf4j
public class TodoBlockResource {

    private final TodoBlockService todoBlockService;
    private final TodoBlockMapper todoBlockMapper;

    @GetMapping("/block")
    public ResponseEntity<List<TodoBlockResponse>> findAll() {
        log.info("Rest request to get all todo blocks");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(todoBlockService.findAll()
                      .stream()
                      .map(todoBlockMapper::toTodoBlockResponse)
                      .collect(Collectors.toList()));
    }

    @PostMapping("/block")
    public ResponseEntity<CreateTodoBlockResponse> addTodoBlock(
        @Valid @NotEmpty @RequestBody CreateTodoBlockRequest createRequest) {
        log.info("Rest request to add todoBlock : {}", createRequest);
        TodoBlockVO todoBlockVO = todoBlockMapper.toTodoBlockVO(createRequest);

        TodoBlockVO savedTodoBlockVO = todoBlockService.save(todoBlockVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(todoBlockMapper.toCreateTodoBlockResponse(savedTodoBlockVO));
    }

    @GetMapping("/block/{todoBlockId}")
    public ResponseEntity<TodoBlockResponse> getTodoBlockById(@PathVariable @Min(1) long todoBlockId) {
        log.info("Rest request to get todoBlock by id : {}", todoBlockId);
        TodoBlockVO todoBlockVO = todoBlockService.findById(todoBlockId);

        return ResponseEntity.status(HttpStatus.FOUND).body(todoBlockMapper.toTodoBlockResponse(todoBlockVO));
    }

    @PutMapping("/block")
    public ResponseEntity<UpdateTodoBlockResponse> updateTodoBlock(
        @Valid @NotEmpty @RequestBody UpdateTodoBlockRequest updateRequest) {

        log.info("Rest request to update todo block : {}", updateRequest);
        TodoBlockVO todoBlockVO = todoBlockMapper.toTodoBlockVO(updateRequest);

        TodoBlockVO updatedTodoBlockVO = todoBlockService.updateTodoBlock(todoBlockVO);

        return ResponseEntity.ok().body(todoBlockMapper.toUpdateTodoBlockResponse(updatedTodoBlockVO));
    }

    @DeleteMapping("/block/{todoBlockId}")
    public ResponseEntity<String> deleteTodoBlock(@PathVariable @Min(1) long todoBlockId) {
        log.info("Rest request to delete todoBlock by id : {}", todoBlockId);
        todoBlockService.deleteById(todoBlockId);

        return ResponseEntity.ok().body("Deleted TodoBlock id " + todoBlockId);
    }
}
