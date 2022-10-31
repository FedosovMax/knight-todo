package com.knighttodo.todocore.service.privatedb.mapper;

import com.knighttodo.todocore.domain.RoutineInstanceVO;
import com.knighttodo.todocore.service.privatedb.representation.RoutineInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineInstanceMapper")
@Mapper(componentModel = "spring")
public interface RoutineInstanceMapper {

    RoutineInstance toRoutineInstance(RoutineInstanceVO routineInstanceVO);

    RoutineInstanceVO toRoutineInstanceVO(RoutineInstance routineInstance);

    @Named("toRoutineInstanceWithoutRoutineTodoInstances")
    @Mapping(target = "routineTodoInstances", ignore = true)
    RoutineInstance toRoutineInstanceWithoutRoutineTodoInstances(RoutineInstanceVO routineInstanceVO);
}
