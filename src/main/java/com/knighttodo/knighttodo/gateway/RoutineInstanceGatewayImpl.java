package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.RoutineInstanceMapper;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.RoutineMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RoutineInstanceGatewayImpl implements RoutineInstanceGateway {

    private final RoutineInstanceRepository routineInstanceRepository;
    private final RoutineInstanceMapper routineInstanceMapper;

    @Override
    public RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO) {
        RoutineInstance savedRoutineInstance = routineInstanceRepository.save(routineInstanceMapper.toRoutineInstance(routineInstanceVO));
        return routineInstanceMapper.toRoutineInstanceVO(savedRoutineInstance);
    }

    @Override
    public List<RoutineInstanceVO> findAll() {
        return routineInstanceRepository.findAll().stream().map(routineInstanceMapper::toRoutineInstanceVO).collect(Collectors.toList());
    }

    @Override
    public Optional<RoutineInstanceVO> findById(UUID routineId) {
        return routineInstanceRepository.findById(routineId).map(routineInstanceMapper::toRoutineInstanceVO);
    }

    @Override
    public void deleteById(UUID routineId) {
        routineInstanceRepository.deleteById(routineId);
    }

//    @Override
//    public List<RoutineInstanceVO> findAllTemplates() {
//        return routineInstanceRepository.findAllTemplates().stream().map(routineInstanceMapper::toRoutineVO).collect(Collectors.toList());
//    }
}
