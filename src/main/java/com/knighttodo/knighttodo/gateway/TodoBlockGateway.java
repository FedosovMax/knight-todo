package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import java.util.List;
import java.util.Optional;

public interface TodoBlockGateway {

    TodoBlock save(TodoBlock todoBlock);

    List<TodoBlock> findAll();

    Optional<TodoBlock> findById(long todoBlockId);

    void deleteById(long todoBlockId);
}
