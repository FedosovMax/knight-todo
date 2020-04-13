package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TodoBlockGatewayImpl implements TodoBlockGateway {

    private final TodoBlockRepository todoBlockRepository;
    private final TodoBlockMapper todoBlockMapper;

    @Override
    public TodoBlockVO save(TodoBlockVO todoBlockVO) {
        TodoBlock savedTodoBlock = todoBlockRepository.save(todoBlockMapper.toTodoBlock(todoBlockVO));
        return todoBlockMapper.toTodoBlockVO(savedTodoBlock);
    }

    @Override
    public List<TodoBlockVO> findAll() {
        return todoBlockRepository.findAll().stream().map(todoBlockMapper::toTodoBlockVO).collect(Collectors.toList());
    }

    @Override
    public Optional<TodoBlockVO> findById(String blockId) {
        return todoBlockRepository.findById(blockId).map(todoBlockMapper::toTodoBlockVO);
    }

    @Override
    public void deleteById(String blockId) {
        todoBlockRepository.deleteById(blockId);
    }
}
