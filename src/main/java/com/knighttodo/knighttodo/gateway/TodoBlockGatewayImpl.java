package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.TodoBlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoBlockGatewayImpl implements TodoBlockGateway {

    private final TodoBlockRepository todoBlockRepository;
    private final TodoBlockMapper todoBlockMapper;

    @Override
    public TodoBlockVO save(TodoBlockVO todoBlockVO) {
        TodoBlock todoBlock = todoBlockRepository.save(todoBlockMapper.toTodoBlock(todoBlockVO));
        return todoBlockMapper.toTodoBlockVO(todoBlock);
    }

    @Override
    public List<TodoBlock> findAll() {
        return todoBlockRepository.findAll();
    }

    @Override
    public Optional<TodoBlockVO> findById(long todoBlockId) {
        return todoBlockRepository.findById(todoBlockId).map(todoBlockMapper::toTodoBlockVO);
    }

    @Override
    public void deleteById(long todoBlockId) {
        todoBlockRepository.deleteById(todoBlockId);
    }
}
