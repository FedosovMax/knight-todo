package com.knighttodo.knighttodo.service.impl;


import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.exception.TodoNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoBlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.service.TodoBlockService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoBlockServiceImpl implements TodoBlockService {

    private final TodoBlockGateway todoBlockGateway;
    private final TodoBlockMapper todoBlockMapper;
    private final TodoMapper todoMapper;

    @Override
    public TodoBlockVO save(TodoBlockVO todoBlockVO) {
        TodoBlock todoBlock = todoBlockGateway.save(todoBlockMapper.toTodoBlock(todoBlockVO));

        return todoBlockMapper.toTodoBlockVO(todoBlock);
    }

    @Override
    public List<TodoBlockVO> findAll() {
        return todoBlockGateway.findAll()
            .stream()
            .map(todoBlockMapper::toTodoBlockVO)
            .collect(Collectors.toList());
    }

    @Override
    public TodoBlockVO findById(long todoBlockId) {
        Optional<TodoBlock> result = todoBlockGateway.findById(todoBlockId);
        TodoBlockVO todoBlock;

        if (result.isPresent()) {
            todoBlock = todoBlockMapper.toTodoBlockVO(result.get());
        } else {
            throw new RuntimeException("Did not find TodoBlock id - " + todoBlockId);
        }

        return todoBlock;
    }

    @Override
    public TodoBlockVO updateTodoBlock(TodoBlockVO changedTodoBlockVO) {

        TodoBlock todoBlock = todoBlockGateway.findById(todoBlockMapper.toTodoBlock(changedTodoBlockVO).getId())
            .orElseThrow(TodoNotFoundException::new);

        todoBlock.setId(changedTodoBlockVO.getId());
        todoBlock.setBlockName(changedTodoBlockVO.getBlockName());
        todoBlock.setTodoList(changedTodoBlockVO.getTodoList()
                                  .stream()
                                  .map(todoMapper::toTodo)
                                  .collect(Collectors.toList()));

        todoBlockGateway.save(todoBlock);

        return todoBlockMapper.toTodoBlockVO(todoBlock);
    }

    @Override
    public void deleteById(long todoBlockId) {
        todoBlockGateway.deleteById(todoBlockId);
    }
}
