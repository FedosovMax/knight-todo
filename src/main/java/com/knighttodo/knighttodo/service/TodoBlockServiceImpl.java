package com.knighttodo.knighttodo.service;


import com.knighttodo.knighttodo.entity.TodoBlock;
import com.knighttodo.knighttodo.entity.exeptions.TodoNotFoundException;
import com.knighttodo.knighttodo.repository.TodoBlockRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public TodoBlock findById(long todoBlockId) {
        Optional<TodoBlock> result = todoBlockRepository.findById(todoBlockId);

        TodoBlock todoBlock;

        if (result.isPresent()){
            todoBlock = result.get();
        }else {
            throw new RuntimeException("Did not find TodoBlock id - " + todoBlockId );
        }

        return todoBlock;
    }

    @Override
    @Transactional
    public TodoBlock updateTodoBlock(TodoBlock changedTodoBlock) {

        final TodoBlock todoBlock = this.todoBlockRepository.findById(changedTodoBlock.getId()).
        orElseThrow(TodoNotFoundException::new); // ???????????????????????

        todoBlock.setBlockName(changedTodoBlock.getBlockName());
        todoBlock.setTodoList(changedTodoBlock.getTodoList());

        return changedTodoBlock;
    }

    @Override
    public void deleteById(long todoBlockId) {
        todoBlockRepository.deleteById(todoBlockId);
    }
}
