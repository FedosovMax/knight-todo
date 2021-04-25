package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.DayTodo;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("DayTodoMapper")
@Mapper(componentModel = "spring", uses = {DayMapper.class})
public interface DayTodoMapper {

    DayTodo toTodo(DayTodoVO dayTodoVO);
    DayTodoVO toTodoVO(DayTodo dayTodo);

    @Named("toDayTodos")
    @IterableMapping(qualifiedByName = "toDayTodoWithoutDay")
    List<DayTodo> toDayTodos(List<DayTodoVO> dayTodoVOS);

    @Named("toDayTodoVOs")
    @IterableMapping(qualifiedByName = "toDayTodoVOWithoutDay")
    List<DayTodoVO> toDayTodoVOs(List<DayTodo> dayTodos);

    @Named("toDayTodoWithoutDay")
    @Mapping(target = "day", ignore = true)
    DayTodo toTodoWithoutDay(DayTodoVO dayTodoVO);

    @Named("toDayTodoVOWithoutDay")
    @Mapping(target = "day", ignore = true)
    DayTodoVO toTodoVOWithoutDay(DayTodo dayTodo);
}
