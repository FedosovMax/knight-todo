package com.knighttodo.todocore.gateway.privatedb.mapper;

import com.knighttodo.todocore.domain.RoutineVO;
import com.knighttodo.todocore.gateway.privatedb.representation.Routine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineMapper")
@Mapper(componentModel = "spring", uses = {RoutineTodoMapper.class})
public interface RoutineMapper {

    @Mapping(target = "routineInstances", source = "routineInstanceVOs")
    Routine toRoutine(RoutineVO routineVO);

    @Mapping(target = "routineInstanceVOs", ignore = true)
    RoutineVO toRoutineVO(Routine routine);

    @Named("toRoutineWithoutRoutineTodos")
    @Mapping(target = "routineTodos", ignore = true)
    Routine toRoutineWithoutRoutineTodo(RoutineVO routine);

    @Named("toRoutineVOWithoutRoutineTodos")
    @Mapping(target = "routineTodos", ignore = true)
    RoutineVO toRoutineWithoutRoutineTodo(Routine routine);
}
