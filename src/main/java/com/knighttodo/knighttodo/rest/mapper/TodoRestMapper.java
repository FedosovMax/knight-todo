package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.dto.request.todo.CreateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.request.todo.UpdateTodoRequestDto;
import com.knighttodo.knighttodo.rest.dto.response.todo.CreateTodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.response.todo.TodoResponseDto;
import com.knighttodo.knighttodo.rest.dto.response.todo.UpdateTodoResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoRestMapper {

    TodoVO toTodoVO(CreateTodoRequestDto requestDto);

    CreateTodoResponseDto toCreateTodoResponseDto(TodoVO todoVO);

    TodoVO toTodoVO(UpdateTodoRequestDto requestDto);

    UpdateTodoResponseDto toUpdateTodoResponseDto(TodoVO todoVO);

    TodoResponseDto toTodoResponseDto(TodoVO todoVO);
}
