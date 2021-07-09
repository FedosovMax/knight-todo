package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.RoutineMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RoutineGatewayImpl implements RoutineGateway {

    private final RoutineRepository routineRepository;
    private final RoutineMapper routineMapper;

    @Override
    public RoutineVO save(RoutineVO routineVO) {
        Routine savedRoutine = routineRepository.save(routineMapper.toRoutine(routineVO));
        return routineMapper.toRoutineVO(savedRoutine);
    }

    @Override
    public List<RoutineVO> findAll() {
        return routineRepository.findAll().stream().map(routineMapper::toRoutineVO).collect(Collectors.toList());
    }

    @Override
    public Optional<RoutineVO> findById(UUID routineId) {
        return routineRepository.findById(routineId).map(routineMapper::toRoutineVO);
    }

    @Override
    public void deleteById(UUID routineId) {
        routineRepository.deleteById(routineId);
    }

//    @Override
//    public List<RoutineVO> findAllTemplates() {
//        return routineRepository.findAllTemplates().stream().map(routineMapper::toRoutineVO).collect(Collectors.toList());
//    }
}
