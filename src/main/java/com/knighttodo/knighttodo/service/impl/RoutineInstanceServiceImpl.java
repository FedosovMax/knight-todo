package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineInstanceGateway;
import com.knighttodo.knighttodo.gateway.RoutineTodoGateway;
import com.knighttodo.knighttodo.service.RoutineInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoutineInstanceServiceImpl implements RoutineInstanceService {

    private final RoutineInstanceGateway routineInstanceGateway;
    private final RoutineTodoGateway routineTodoGateway;

    @Override
    public RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO) {
        return routineInstanceGateway.save(routineInstanceVO);
    }

    @Override
    public List<RoutineInstanceVO> findAll() {
        return routineInstanceGateway.findAll();
    }

    @Override
    public RoutineInstanceVO findById(UUID routineInstanceId) {
        return routineInstanceGateway.findById(routineInstanceId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Instance with such id:%s can't be " + "found", routineInstanceId));
                    return new RoutineNotFoundException(
                            String.format("Routine Instance with such id:%s can't be " + "found", routineInstanceId));
                });
    }

    @Override
    public RoutineInstanceVO update(UUID routineInstanceId, RoutineInstanceVO changedRoutineInstanceVO) {
        RoutineInstanceVO routineInstanceVO = findById(routineInstanceId);
        synchronizeRoutineTodosInRoutineVO(routineInstanceVO, changedRoutineInstanceVO);
        routineInstanceVO.setName(changedRoutineInstanceVO.getName());
        routineInstanceVO.setHardness(changedRoutineInstanceVO.getHardness());
        routineInstanceVO.setScariness(changedRoutineInstanceVO.getScariness());
        routineInstanceVO.setReady(changedRoutineInstanceVO.isReady());
        return routineInstanceGateway.save(routineInstanceVO);
    }

    private void synchronizeRoutineTodosInRoutineVO(RoutineInstanceVO routineInstanceVO, RoutineInstanceVO changedRoutineInstanceVO) {
        unmapRoutineTodosExcludedFromRoutine(routineInstanceVO, changedRoutineInstanceVO);
        mapRoutineTodosAddedToRoutine(routineInstanceVO, changedRoutineInstanceVO);
    }

    private void unmapRoutineTodosExcludedFromRoutine(RoutineInstanceVO routineInstanceVO, RoutineInstanceVO changedRoutineInstanceVO) {
        List<UUID> changedRoutineVOTodoIds = extractTodoIds(changedRoutineInstanceVO);
        routineInstanceVO.getRoutineTodos().stream()
                .filter(routineTodoVO -> !changedRoutineVOTodoIds.contains(routineTodoVO.getId()))
                .forEach(routineTodoVO -> routineTodoVO.setRoutine(null));
    }

    private List<UUID> extractTodoIds(RoutineInstanceVO routineInstanceVO) {
        return routineInstanceVO.getRoutineTodos().stream().map(RoutineTodoVO::getId).collect(Collectors.toList());
    }

    private void mapRoutineTodosAddedToRoutine(RoutineInstanceVO routineInstanceVO, RoutineInstanceVO changedRoutineInstanceVO) {
        List<UUID> routineVOTodoIds = extractTodoIds(routineInstanceVO);
        List<UUID> addedRoutineTodoIds = extractTodoIds(changedRoutineInstanceVO).stream()
                .filter(routineTodoId -> !routineVOTodoIds.contains(routineTodoId))
                .collect(Collectors.toList());
        routineInstanceVO.getRoutineTodos().addAll(fetchRoutineTodosByIds(addedRoutineTodoIds));
    }

    private List<RoutineTodoVO> fetchRoutineTodosByIds(List<UUID> routineTodoIds) {
        return routineTodoIds.stream()
                .map(routineTodoGateway::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID routineId) {
        routineInstanceGateway.deleteById(routineId);
    }
}
