package com.knighttodo.knighttodo.gateway;

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

    @Override
    public TodoBlock save(TodoBlock todoBlock) {
        return todoBlockRepository.save(todoBlock);
    }

    @Override
    public List<TodoBlock> findAll() {
        return todoBlockRepository.findAll();
    }

    @Override
    public Optional<TodoBlock> findById(long todoBlockId) {
        return todoBlockRepository.findById(todoBlockId);
    }

    @Override
    public void deleteById(long todoBlockId) {
        todoBlockRepository.deleteById(todoBlockId);
    }
}
