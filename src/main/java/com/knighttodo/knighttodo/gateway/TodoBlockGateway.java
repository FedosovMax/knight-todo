package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import java.util.List;
import java.util.Optional;

public interface TodoBlockGateway {

    TodoBlockVO save(TodoBlockVO todoBlockVO);

    List<TodoBlockVO> findAll();

    Optional<TodoBlockVO> findById(long blockId);

    void deleteById(long blockId);
}
