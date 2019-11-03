package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.service.TodoBlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("blocks")
@Slf4j
public class TodoBlockController {

    final private TodoBlockService todoBlockService;

    @GetMapping("/block")
    public ResponseEntity<List<TodoBlock>> findAll() {
        log.info("Rest request to get all todo blocks");
        List<TodoBlock> todoBlocks = todoBlockService.findAll();

        if (todoBlocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            List<TodoBlock> todoBlocksAfterCheck = new ArrayList<>(todoBlocks);

            return new ResponseEntity<>(todoBlocksAfterCheck, HttpStatus.FOUND);
        }
    }

    @PostMapping("/block")
    public ResponseEntity<TodoBlock> addTodoBlock(@RequestBody TodoBlock todoBlock) {
        log.info("Rest request to add todoBlock : {}", todoBlock);
        todoBlockService.save(todoBlock);

        return new ResponseEntity<>(todoBlock, HttpStatus.CREATED);
    }

    @GetMapping("/block/{todoBlockId}")
    public ResponseEntity<TodoBlock> getTodoBlockById(@PathVariable long todoBlockId) {
        log.info("Rest request to get todoBlock by id : {}", todoBlockId);
        TodoBlock todoBlock = todoBlockService.findById(todoBlockId);

        if (todoBlock == null) {
            throw new RuntimeException("TodoBlock id not found - " + todoBlockId);
        }

        return new ResponseEntity<>(todoBlock, HttpStatus.FOUND);
    }

    @PutMapping("/block")
    public ResponseEntity<TodoBlock> updateTodoBlock(@Valid @RequestBody TodoBlock todoBlock) {
        log.info("Rest request to update todo block : {}", todoBlock );
        return new ResponseEntity<>(this.todoBlockService.updateTodoBlock(todoBlock), HttpStatus.OK);
    }

    @DeleteMapping("/block/{todoBlockId}")
    public ResponseEntity<String> deleteTodoBlock(@PathVariable long todoBlockId) {
        log.info("Rest request to delete todoBlock by id : {}", todoBlockId);
        todoBlockService.deleteById(todoBlockId);

        return new ResponseEntity<>("Deleted TodoBlock id " + todoBlockId, HttpStatus.OK);
    }
}
