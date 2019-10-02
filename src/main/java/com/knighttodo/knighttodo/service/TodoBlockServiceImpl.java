package com.knighttodo.knighttodo.service;


import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoBlockServiceImpl implements TodoBlockService {

    final private TodoBlockRepository todoBlockRepository;

    public TodoBlockServiceImpl(TodoBlockRepository todoBlockRepository) {
        this.todoBlockRepository = todoBlockRepository;
    }

    @Override
    public void save(TodoBlock todoBlock) {
        todoBlockRepository.save(todoBlock);
    }

    @Override
    public List<TodoBlock> findAll() {
        return todoBlockRepository.findAll();
    }
}
