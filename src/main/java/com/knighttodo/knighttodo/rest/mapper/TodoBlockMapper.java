package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.gateway.privatedb.representation.TodoBlock;
import com.knighttodo.knighttodo.rest.request.todoblock.CreateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.request.todoblock.UpdateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.response.todoblock.CreateTodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.UpdateTodoBlockResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoBlockMapper {

    TodoBlock toTodoBlock(CreateTodoBlockRequest request);

    CreateTodoBlockResponse toCreateTodoBlockResponse(TodoBlock todoBlock);

    TodoBlock toTodoBlock(UpdateTodoBlockRequest request);

    UpdateTodoBlockResponse toUpdateTodoBlockResponse(TodoBlock todoBlock);
}
