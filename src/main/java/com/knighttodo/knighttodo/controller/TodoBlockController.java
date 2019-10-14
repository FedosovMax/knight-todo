package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.service.TodoBlockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("blocks")
public class TodoBlockController {

    final private TodoBlockService todoBlockService;

    public TodoBlockController(TodoBlockService todoBlockService) {
        this.todoBlockService = todoBlockService;
    }

    @GetMapping("/block")
    public ResponseEntity<List<TodoBlock>> findAll() {
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
        todoBlockService.save(todoBlock);

        return new ResponseEntity<>(todoBlock, HttpStatus.CREATED);
    }

    @GetMapping("/block/{todoBlockId}")
    public ResponseEntity<TodoBlock> getTodoBlockById(@PathVariable long todoBlockId) {

        TodoBlock todoBlock = todoBlockService.findById(todoBlockId);

        if (todoBlock == null) {
            throw new RuntimeException("TodoBlock id not found - " + todoBlockId);
        }

        return new ResponseEntity<>(todoBlock, HttpStatus.FOUND);
    }

    @PutMapping("/block")
    public ResponseEntity<TodoBlock> updateTodoBlock(@Valid @RequestBody TodoBlock todoBlock) {

        return new ResponseEntity<>(this.todoBlockService.updateTodoBlock(todoBlock), HttpStatus.OK);
    }

    @DeleteMapping("/block/{todoBlockId}")
    public ResponseEntity<String> deleteTodoBlock(@PathVariable long todoBlockId) {

        todoBlockService.deleteById(todoBlockId);

        return new ResponseEntity<>("Deleted TodoBlock id " + todoBlockId, HttpStatus.OK);
    }
}
