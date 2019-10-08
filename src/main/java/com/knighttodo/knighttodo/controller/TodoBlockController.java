package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.Todo;
import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.service.TodoBlockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("todoBlockController")
public class TodoBlockController {

    final private TodoBlockService todoBlockService;

    final private TodoBlockRepository todoBlockRepository;

    public TodoBlockController(TodoBlockService todoBlockService, TodoBlockRepository todoBlockRepository) {
        this.todoBlockService = todoBlockService;
        this.todoBlockRepository = todoBlockRepository;
    }

    @GetMapping("/block")
    public ResponseEntity<List<TodoBlock>> findAll(){
        List<TodoBlock> todoBlocks = todoBlockService.findAll();

        if (todoBlocks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            List<TodoBlock> todoBlocksAfterCheck = new ArrayList<>(todoBlocks);

            return new ResponseEntity<>(todoBlocksAfterCheck, HttpStatus.FOUND);
        }
    }
    @PostMapping("/block")
    public ResponseEntity<TodoBlock> addCategory(@RequestBody TodoBlock todoBlock){
        todoBlockService.save(todoBlock);

        return new ResponseEntity<>(todoBlock, HttpStatus.CREATED);
    }

    @GetMapping("/block/{todoBlockId}")
    public ResponseEntity<TodoBlock> getCategoryById(@PathVariable long todoBlockId){

        TodoBlock todoBlock = todoBlockService.findById(todoBlockId);

        if (todoBlock == null){
            throw new RuntimeException("TodoBlock id not found - " + todoBlockId);
        }

        return new ResponseEntity<>(todoBlock, HttpStatus.FOUND);
    }

    @PutMapping("/block/")
    public ResponseEntity<TodoBlock> updateCategory(@Valid @RequestBody TodoBlock todoBlock){

        return new ResponseEntity<>(this.todoBlockService.updateTodoBlock(todoBlock), HttpStatus.OK);
    }

    @DeleteMapping("/block/{todoBlockId}")
    public ResponseEntity<String> deleteCategory(@PathVariable long todoBlockId){

        todoBlockService.deleteById(todoBlockId);

        return new ResponseEntity<>("Deleted TodoBlock id " + todoBlockId, HttpStatus.OK);
    }
}
