package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.exception.TodoBlockNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoBlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.service.TodoBlockService;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoBlockServiceImpl implements TodoBlockService {

    private final TodoBlockGateway todoBlockGateway;
    private final TodoBlockMapper todoBlockMapper;

    @Override
    public TodoBlockVO save(TodoBlockVO todoBlockVO) {
        return todoBlockGateway.save(todoBlockVO);
    }

    @Override
    public List<TodoBlockVO> findAll() {
        return todoBlockGateway.findAll();
    }

    @Override
    public TodoBlockVO findById(String blockId) {
        return todoBlockGateway.findById(blockId)
            .orElseThrow(() -> new TodoBlockNotFoundException(
                String.format("TodoBlock with such id:%s can't be " + "found", blockId)));
    }

    @Override
    public TodoBlockVO updateTodoBlock(TodoBlockVO changedTodoBlockVO) {
        TodoBlockVO todoBlockVO = todoBlockGateway.findById(todoBlockMapper.toTodoBlock(changedTodoBlockVO).getId())
            .orElseThrow(() -> new TodoBlockNotFoundException(
                String.format("Block with such id:%s is not found", changedTodoBlockVO.getId())));

        todoBlockVO.setId(changedTodoBlockVO.getId());
        todoBlockVO.setBlockName(changedTodoBlockVO.getBlockName());
        return todoBlockGateway.save(todoBlockVO);
    }

    @Override
    public void deleteById(String blockId) {
        todoBlockGateway.deleteById(blockId);
    }
}
