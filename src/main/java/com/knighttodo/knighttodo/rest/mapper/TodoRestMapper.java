package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.dto.todo.request.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.request.UpdateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.CreateTodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.TodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.todo.response.UpdateTodoResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TodoRestMapper {

    TodoVO toTodoVO(CreateTodoRequestDto requestDto);

    @Mapping(target = "ready", source = "ready")
    CreateTodoResponseDto toCreateTodoResponseDto(TodoVO todoVO);

    @Mapping(target = "ready", source = "ready")
    TodoVO toTodoVO(UpdateTodoRequestDto requestDto);

    @Mapping(target = "ready", source = "ready")
    UpdateTodoResponseDto toUpdateTodoResponseDto(TodoVO todoVO);

    @Mapping(target = "ready", source = "ready")
    TodoResponseDto toTodoResponseDto(TodoVO todoVO);

}
