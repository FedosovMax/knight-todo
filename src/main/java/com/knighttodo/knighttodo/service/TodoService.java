package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.entity.Todo;

import java.util.List;

public interface TodoService {

    void save(Todo Todo);

    List<Todo> findAll();
}
