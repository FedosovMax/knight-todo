package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineGateway;
import com.knighttodo.knighttodo.gateway.RoutineTodoGateway;
import com.knighttodo.knighttodo.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoutineServiceImpl implements RoutineService {

    private final RoutineGateway routineGateway;
    private final RoutineTodoGateway routineTodoGateway;

    @Override
    public RoutineVO save(RoutineVO routineVO) {
        return routineGateway.save(routineVO);
    }

    @Override
    public List<RoutineVO> findAll() {
        return routineGateway.findAll();
    }

    @Override
    public RoutineVO findById(String routineId) {
        return routineGateway.findById(routineId)
            .orElseThrow(() -> new RoutineNotFoundException(
                String.format("Routine with such id:%s can't be " + "found", routineId)));
    }

    @Override
    public RoutineVO updateRoutine(String routineId, RoutineVO changedRoutineVO) {
        RoutineVO routineVO = findById(routineId);
        synchronizeRoutineTodosInRoutineVO(routineVO, changedRoutineVO);
        routineVO.setName(changedRoutineVO.getName());
        routineVO.setTemplateId(routineId);
        routineVO.setHardness(changedRoutineVO.getHardness());
        routineVO.setScariness(changedRoutineVO.getScariness());
        routineVO.setReady(changedRoutineVO.isReady());
        return routineGateway.save(routineVO);
    }

    private void synchronizeRoutineTodosInRoutineVO(RoutineVO routineVO, RoutineVO changedRoutineVO) {
        unmapRoutineTodosExcludedFromRoutine(routineVO, changedRoutineVO);
        mapRoutineTodosAddedToRoutine(routineVO, changedRoutineVO);
    }

    private void unmapRoutineTodosExcludedFromRoutine(RoutineVO routineVO, RoutineVO changedRoutineVO) {
        List<String> changedRoutineVOTodoIds = extractTodoIds(changedRoutineVO);
        routineVO.getRoutineTodos().stream()
            .filter(routineTodoVO -> !changedRoutineVOTodoIds.contains(routineTodoVO.getId()))
            .forEach(routineTodoVO -> routineTodoVO.setRoutine(null));
    }

    private List<String> extractTodoIds(RoutineVO routineVO) {
        return routineVO.getRoutineTodos().stream().map(RoutineTodoVO::getId).collect(Collectors.toList());
    }

    private void mapRoutineTodosAddedToRoutine(RoutineVO routineVO, RoutineVO changedRoutineVO) {
        List<String> routineVOTodoIds = extractTodoIds(routineVO);
        List<String> addedRoutineTodoIds = extractTodoIds(changedRoutineVO).stream()
            .filter(routineTodoId -> !routineVOTodoIds.contains(routineTodoId))
            .collect(Collectors.toList());
        routineVO.getRoutineTodos().addAll(fetchRoutineTodosByIds(addedRoutineTodoIds));
    }

    private List<RoutineTodoVO> fetchRoutineTodosByIds(List<String> routineTodoIds) {
        return routineTodoIds.stream()
                .map(routineTodoGateway::findById)
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String routineId) {
        routineGateway.deleteById(routineId);
    }

    @Override
    public List<RoutineVO> findAllTemplates() {
        return routineGateway.findAllTemplates();
    }
}
