package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineGateway;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.BlockService;
import com.knighttodo.knighttodo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoutineServiceImpl implements RoutineService {

    private final RoutineGateway routineGateway;
    private final BlockService blockService;
    private final TodoService todoService;

    @Override
    public RoutineVO save(String blockId, RoutineVO routineVO) {
        routineVO.setBlock(blockService.findById(blockId));
        routineVO.setTodos(fetchTodosByIds(extractTodoIds(routineVO)));
        routineVO.getTodos().forEach((todoVO -> todoVO.setRoutineVO(routineVO)));
        RoutineVO dbRoutineVO = routineGateway.save(routineVO);
        dbRoutineVO.setTemplateId(dbRoutineVO.getId());
        dbRoutineVO = routineGateway.save(dbRoutineVO);
        return dbRoutineVO;
    }

    private List<TodoVO> fetchTodosByIds(List<String> todoIds) {
        return todoIds.stream().map(todoService::findById).collect(Collectors.toList());
    }

    private List<String> extractTodoIds(RoutineVO routineVO) {
        return routineVO.getTodos().stream().map(TodoVO::getId).collect(Collectors.toList());
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
    public RoutineVO updateRoutine(String blockId, String routineId, RoutineVO changedRoutineVO) {
        RoutineVO routineVO = findById(routineId);
        synchronizeTodosInRoutineVO(routineVO, changedRoutineVO);
        routineVO.setBlock(blockService.findById(blockId));
        routineVO.setName(changedRoutineVO.getName());
        routineVO.setTemplateId(routineId);
        routineVO.setHardness(changedRoutineVO.getHardness());
        routineVO.setScariness(changedRoutineVO.getScariness());
        routineVO.setReady(changedRoutineVO.isReady());
        return routineGateway.save(routineVO);
    }

    private void synchronizeTodosInRoutineVO(RoutineVO routineVO, RoutineVO changedRoutineVO) {
        removeTodosFromRoutineIfNecessary(routineVO, changedRoutineVO);
        addTodosToRoutineIfNecessary(routineVO, changedRoutineVO);
    }

    private void removeTodosFromRoutineIfNecessary(RoutineVO routineVO, RoutineVO changedRoutineVO) {
        List<String> changedRoutineVOTodoIds = extractTodoIds(changedRoutineVO);
        routineVO.getTodos().stream()
            .filter(todoVO -> !changedRoutineVOTodoIds.contains(todoVO.getId()))
            .forEach(todoVO -> todoVO.setRoutineVO(null));
    }

    private void addTodosToRoutineIfNecessary(RoutineVO routineVO, RoutineVO changedRoutineVO) {
        List<String> routineVOTodoIds = extractTodoIds(routineVO);
        List<String> addedTodoIds = extractTodoIds(changedRoutineVO).stream()
            .filter(todoId -> !routineVOTodoIds.contains(todoId))
            .collect(Collectors.toList());
        routineVO.getTodos().addAll(fetchTodosByIds(addedTodoIds));
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
