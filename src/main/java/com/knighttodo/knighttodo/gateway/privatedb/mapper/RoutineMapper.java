package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineMapper")
@Mapper(componentModel = "spring", uses = {BlockMapper.class, TodoMapper.class})
public interface RoutineMapper {

    @Mapping(target = "todos", qualifiedByName = {"TodoMapper", "toTodos"})
    Routine toRoutine(RoutineVO routineVO);

    @Mapping(target = "todos", qualifiedByName = {"TodoMapper", "toTodoVOs"})
    RoutineVO toRoutineVO(Routine routine);

    @Named("toRoutineWithoutBlock")
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "todos", qualifiedByName = {"TodoMapper", "toTodos"})
    Routine toRoutineWithoutBlock(RoutineVO routineVO);

    @Named("toRoutineVOWithoutBlock")
    @Mapping(target = "block", ignore = true)
    @Mapping(target = "todos", qualifiedByName = {"TodoMapper", "toTodoVOs"})
    RoutineVO toRoutineVOWithoutBlock(Routine routine);
}
