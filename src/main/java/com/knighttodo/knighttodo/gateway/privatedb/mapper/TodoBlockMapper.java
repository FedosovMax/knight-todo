package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoBlockMapper {

    TodoBlock toTodoBlock(TodoBlockVO todoBlockVO);

    TodoBlockVO toTodoBlockVO(TodoBlock todoBlock);
}
