package com.knighttodo.todocore.gateway.privatedb.mapper;

import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.gateway.privatedb.representation.RoutineTodo;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Named("RoutineTodoMapper")
@Mapper(componentModel = "spring", uses = {RoutineInstanceMapper.class, RoutineMapper.class})
public interface RoutineTodoMapper {

    @Mapping(target = "routine", source = "routineVO", qualifiedByName = {"toRoutineWithoutRoutineTodos"})
    RoutineTodo toRoutineTodo(RoutineTodoVO routineTodoVO);

    @Mapping(target = "routineVO", source = "routine", qualifiedByName = {"toRoutineVOWithoutRoutineTodos"})
    RoutineTodoVO toRoutineTodoVO(RoutineTodo routineTodo);

    @Named("toRoutineTodos")
    @IterableMapping(qualifiedByName = "toRoutineTodoWithoutRoutine")
    List<RoutineTodo> toRoutineTodos(List<RoutineTodoVO> routineTodoVOS);

    @Named("toRoutineTodoVOs")
    @IterableMapping(qualifiedByName = "toRoutineTodoVOWithoutRoutine")
    List<RoutineTodoVO> toRoutineTodoVOs(List<RoutineTodo> routineTodos);

    @Named("toRoutineTodoWithoutRoutine")
    @Mapping(target = "routine", ignore = true)
    RoutineTodo toRoutineTodoWithoutRoutine(RoutineTodoVO routineTodoVO);

    @Named("toRoutineTodoVOWithoutRoutine")
    @Mapping(target = "routineVO", ignore = true)
    RoutineTodoVO toRoutineTodoVOWithoutRoutine(RoutineTodo routineTodo);

    @Named("toRoutineTodoVOWithoutRoutineAndRoutineTodoInstances")
    @Mapping(target = "routineVO", ignore = true)
    @Mapping(target = "routineTodoInstances", ignore = true)
    RoutineTodoVO toRoutineTodoVOWithoutRoutineAndRoutineTodoInstances(RoutineTodo routineTodo);
}
