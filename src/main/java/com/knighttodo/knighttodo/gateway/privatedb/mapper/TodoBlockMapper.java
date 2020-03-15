package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface TodoBlockMapper {

    TodoBlock toTodoBlock(TodoBlockVO todoBlockVO);

    TodoBlockVO toTodoBlockVO(TodoBlock todoBlock);

    default Optional<TodoBlockVO> toOptionalTodoBlockVO(Optional<TodoBlock> optionalTodoBlock) {
        if(optionalTodoBlock.isPresent()) {
            return Optional.of(toTodoBlockVO(optionalTodoBlock.get()));
        }
        return Optional.empty();
    }
}
