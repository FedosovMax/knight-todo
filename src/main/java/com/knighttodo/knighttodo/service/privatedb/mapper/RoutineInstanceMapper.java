package com.knighttodo.knighttodo.service.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.service.privatedb.representation.RoutineInstance;
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
