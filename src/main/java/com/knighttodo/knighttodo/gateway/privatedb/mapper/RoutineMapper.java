package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoutineMapper {

    Routine toRoutine(RoutineVO routineVO);

    RoutineVO toRoutineVO(Routine routine);
}
