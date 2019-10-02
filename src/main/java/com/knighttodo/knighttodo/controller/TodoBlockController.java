package com.knighttodo.knighttodo.controller;

import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.service.TodoBlockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("todoBlockController")
public class TodoBlockController {

    final private TodoBlockService todoBlockService;

    public TodoBlockController(TodoBlockService todoBlockService) {
        this.todoBlockService = todoBlockService;
    }

    @GetMapping(name = "todoBlock")
    public String getHello() {
        return "Hello!";
    }

    @GetMapping("/getAll")
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

    @PostMapping("/add")
    public ResponseEntity<TodoBlock> addCategory(@RequestBody TodoBlock todoBlock){
        todoBlockService.save(todoBlock);

        return new ResponseEntity<>(todoBlock, HttpStatus.CREATED);
    }
}
