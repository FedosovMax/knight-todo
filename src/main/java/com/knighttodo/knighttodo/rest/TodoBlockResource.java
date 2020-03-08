package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.rest.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.rest.request.todoblock.CreateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.request.todoblock.UpdateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.response.todoblock.CreateTodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.GetTodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.UpdateTodoBlockResponse;
import com.knighttodo.knighttodo.service.TodoBlockService;

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
@RequestMapping("blocks")
@Slf4j
public class TodoBlockResource {

    private final TodoBlockService todoBlockService;
    private final TodoBlockMapper todoBlockMapper;

    @GetMapping("/block")
    public ResponseEntity<List<GetTodoBlockResponse>> findAll() {
        log.info("Rest request to get all todo blocks");

        List<GetTodoBlockResponse> response = todoBlockService.findAll()
            .stream()
            .map(todoBlockMapper::toGetTodoBlockResponse)
            .collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PostMapping("/block")
    public ResponseEntity<CreateTodoBlockResponse> addTodoBlock(@RequestBody CreateTodoBlockRequest createRequest) {
        log.info("Rest request to add todoBlock : {}", createRequest);
        TodoBlockVO todoBlockVO = todoBlockMapper.toTodoBlockVO(createRequest);

        TodoBlockVO savedTodoBlockVO = todoBlockService.save(todoBlockVO);
        CreateTodoBlockResponse response = todoBlockMapper.toCreateTodoBlockResponse(savedTodoBlockVO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/block/{todoBlockId}")
    public ResponseEntity<GetTodoBlockResponse> getTodoBlockById(@PathVariable @Min(1) long todoBlockId) {
        log.info("Rest request to get todoBlock by id : {}", todoBlockId);
        TodoBlockVO todoBlockVO = todoBlockService.findById(todoBlockId);

        GetTodoBlockResponse response = todoBlockMapper.toGetTodoBlockResponse(todoBlockVO);

        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PutMapping("/block")
    public ResponseEntity<UpdateTodoBlockResponse> updateTodoBlock(
        @Valid @RequestBody UpdateTodoBlockRequest updateRequest) {

        log.info("Rest request to update todo block : {}", updateRequest);
        TodoBlockVO todoBlockVO = todoBlockMapper.toTodoBlockVO(updateRequest);

        TodoBlockVO updatedTodoBlockVO = todoBlockService.updateTodoBlock(todoBlockVO);
        UpdateTodoBlockResponse response = todoBlockMapper.toUpdateTodoBlockResponse(updatedTodoBlockVO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/block/{todoBlockId}")
    public ResponseEntity<String> deleteTodoBlock(@PathVariable @Min(1) long todoBlockId) {
        log.info("Rest request to delete todoBlock by id : {}", todoBlockId);
        todoBlockService.deleteById(todoBlockId);

        return new ResponseEntity<>("Deleted TodoBlock id " + todoBlockId, HttpStatus.OK);
    }
}
