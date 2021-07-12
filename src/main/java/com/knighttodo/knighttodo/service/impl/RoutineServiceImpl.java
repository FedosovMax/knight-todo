package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineGateway;
import com.knighttodo.knighttodo.service.RoutineInstanceService;
import com.knighttodo.knighttodo.service.RoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoutineServiceImpl implements RoutineService {

    private final RoutineGateway routineGateway;
    private final RoutineInstanceService routineInstanceService;

    @Override
    public RoutineVO save(RoutineVO routineVO) {
        return routineGateway.save(routineVO);
    }

    @Override
    public List<RoutineVO> findAll() {
        return routineGateway.findAll();
    }

    @Override
    public RoutineVO findById(UUID routineId) {
        return routineGateway.findById(routineId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine with such id:%s can't be " + "found", routineId));
                    return new RoutineNotFoundException(
                            String.format("Routine with such id:%s can't be " + "found", routineId));
                });
    }

    @Override
    public RoutineVO updateRoutine(UUID routineId, RoutineVO changedRoutineVO) {
        RoutineVO routineVO = findById(routineId);
        routineVO.setName(changedRoutineVO.getName());
        routineVO.setHardness(changedRoutineVO.getHardness());
        routineVO.setScariness(changedRoutineVO.getScariness());
        return routineGateway.save(routineVO);
    }

    @Override
    public void deleteById(UUID routineId) {
        routineGateway.deleteById(routineId);
    }

}
