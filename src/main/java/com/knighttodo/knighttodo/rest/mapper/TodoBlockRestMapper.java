package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.rest.dto.request.todoblock.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.request.todoblock.UpdateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.response.todoblock.CreateTodoBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.response.todoblock.TodoBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.response.todoblock.UpdateTodoBlockResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoBlockRestMapper {

    TodoBlockVO toTodoBlockVO(CreateTodoBlockRequestDto requestDto);

    CreateTodoBlockResponseDto toCreateTodoBlockResponseDto(TodoBlockVO todoBlockVO);

    TodoBlockVO toTodoBlockVO(UpdateTodoBlockRequestDto requestDto);

    UpdateTodoBlockResponseDto toUpdateTodoBlockResponseDto(TodoBlockVO todoBlockVO);

    TodoBlockResponseDto toTodoBlockResponseDto(TodoBlockVO todoBlockVO);
}
