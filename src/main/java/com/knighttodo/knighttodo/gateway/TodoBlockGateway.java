package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import java.util.List;
import java.util.Optional;

public interface TodoBlockGateway {

    TodoBlockVO save(TodoBlockVO todoBlockVO);

    List<TodoBlock> findAll();

    Optional<TodoBlockVO> findById(long todoBlockId);

    void deleteById(long todoBlockId);
}
