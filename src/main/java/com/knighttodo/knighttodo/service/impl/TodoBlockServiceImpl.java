package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.exception.TodoBlockNotFoundException;
import com.knighttodo.knighttodo.gateway.TodoBlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.TodoBlockMapper;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.TodoBlockService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TodoBlockServiceImpl implements TodoBlockService {

    private final TodoBlockGateway todoBlockGateway;
    private final RoutineService routineService;
    private final TodoBlockMapper todoBlockMapper;

    @Override
    public TodoBlockVO save(TodoBlockVO todoBlockVO) {
        todoBlockVO = todoBlockGateway.save(todoBlockVO);
        fetchRoutines(todoBlockVO);
        return todoBlockVO;
    }

    @Override
    public List<TodoBlockVO> findAll() {
        List<TodoBlockVO> blockVOS = todoBlockGateway.findAll();
        blockVOS.forEach(this::fetchRoutines);
        return blockVOS;
    }

    @Override
    public TodoBlockVO findById(String blockId) {
        TodoBlockVO todoBlockVO = todoBlockGateway.findById(blockId)
            .orElseThrow(() -> new TodoBlockNotFoundException(
                String.format("TodoBlock with such id:%s can't be " + "found", blockId)));
        return fetchRoutines(todoBlockVO);
    }

    @Override
    public TodoBlockVO updateTodoBlock(String blockId, TodoBlockVO changedTodoBlockVO) {
        changedTodoBlockVO.setId(blockId);
        TodoBlockVO todoBlockVO = todoBlockGateway.findById(todoBlockMapper.toTodoBlock(changedTodoBlockVO).getId())
            .orElseThrow(() -> new TodoBlockNotFoundException(
                String.format("Block with such id:%s is not found", blockId)));

        todoBlockVO.setBlockName(changedTodoBlockVO.getBlockName());
        fetchRoutines(todoBlockVO);
        return todoBlockGateway.save(todoBlockVO);
    }

    @Override
    public void deleteById(String blockId) {
        todoBlockGateway.deleteById(blockId);
    }

    private TodoBlockVO fetchRoutines(TodoBlockVO todoBlockVO) {
        List<RoutineVO> routines = routineService.findAllPatterns()
            .stream().map(this::copyRoutine).collect(Collectors.toList());
        todoBlockVO.setRoutines(routines);
        return todoBlockVO;
    }

    private RoutineVO copyRoutine(RoutineVO routineVO) {
        RoutineVO routine = new RoutineVO();
        routine.setTemplateId(routineVO.getTemplateId());
        routine.setHardness(routineVO.getHardness());
        routine.setScariness(routineVO.getScariness());
        routine.setName(routineVO.getName());
        routine.setTodos(routineVO.getTodos());
        routine.setTodoBlock(routineVO.getTodoBlock());
        return routine;
    }
}
