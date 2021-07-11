package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineMapper")
@Mapper(componentModel = "spring", uses = {RoutineTodoMapper.class})
public interface RoutineMapper {

    @Mapping(target = "routineInstances", source = "routineInstanceVOs")
    Routine toRoutine(RoutineVO routineVO);

    @Mapping(target = "routineInstanceVOs", source = "routineInstances")
    RoutineVO toRoutineVO(Routine routine);
}
