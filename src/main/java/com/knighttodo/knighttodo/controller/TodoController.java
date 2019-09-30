package com.knighttodo.knighttodo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class TodoController {

    @GetMapping("todo")
    public String getHello() {
        return "Hello!";
    }

}
