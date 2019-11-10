package com.knighttodo.knighttodo.gateway.privatedb;

import com.knighttodo.knighttodo.domain.TodoVO;

public interface TodoGateway {

    TodoVO save(TodoVO todoVO);
}
