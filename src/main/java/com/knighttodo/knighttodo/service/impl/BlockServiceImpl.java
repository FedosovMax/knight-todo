package com.knighttodo.knighttodo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.BlockNotFoundException;
import com.knighttodo.knighttodo.gateway.BlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.BlockMapper;
import com.knighttodo.knighttodo.service.BlockService;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.TodoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {

    private final BlockGateway blockGateway;
    private final BlockMapper blockMapper;
    private RoutineService routineService;
    private TodoService todoService;

    @Autowired
    public void setRoutineService(RoutineService routineService) {
        this.routineService = routineService;
    }

    @Autowired
    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
    }

    @Override
    public BlockVO save(BlockVO blockVO) {
        BlockVO savedBlock = blockGateway.save(blockVO);
        fetchRoutines(savedBlock);
        mapTodosFromRoutinesToBlock(savedBlock);
        return savedBlock;
    }

    @Override
    public List<BlockVO> findAll() {
        return blockGateway.findAll();
    }

    @Override
    public BlockVO findById(String blockId) {
        return blockGateway.findById(blockId)
            .orElseThrow(() -> new BlockNotFoundException(
                String.format("Block with such id:%s can't be " + "found", blockId)));
    }

    @Override
    public BlockVO updateBlock(String blockId, BlockVO changedBlockVO) {
        changedBlockVO.setId(blockId);
        BlockVO blockVO = blockGateway.findById(blockMapper.toBlock(changedBlockVO).getId())
            .orElseThrow(() -> new BlockNotFoundException(
                String.format("Block with such id:%s is not found", blockId)));

        blockVO.setBlockName(changedBlockVO.getBlockName());
        return blockGateway.save(blockVO);
    }

    @Override
    public void deleteById(String blockId) {
        blockGateway.deleteById(blockId);
    }

    private void fetchRoutines(BlockVO blockVO) {
        List<RoutineVO> routineTemplates = routineService.findAllTemplates();
        List<RoutineVO> copiedRoutines = routineTemplates.stream().map(routineTemplate -> copyRoutine(blockVO, routineTemplate))
            .collect(Collectors.toList());
        blockVO.setRoutines(copiedRoutines);
    }

    private RoutineVO copyRoutine(BlockVO blockVO, RoutineVO routineTemplate) {
        RoutineVO copiedRoutineVO = new RoutineVO();
        copiedRoutineVO.setHardness(routineTemplate.getHardness());
        copiedRoutineVO.setScariness(routineTemplate.getScariness());
        copiedRoutineVO.setTemplateId(routineTemplate.getTemplateId());
        copiedRoutineVO.setName(routineTemplate.getName());
        copiedRoutineVO.setBlock(blockVO);

        copiedRoutineVO = routineService.save(blockVO.getId(), copiedRoutineVO);
        return copyTodosToRoutine(copiedRoutineVO, routineTemplate);
    }

    private RoutineVO copyTodosToRoutine(RoutineVO copiedRoutineVO, RoutineVO routineVO) {
        copiedRoutineVO
            .setTodos(routineVO.getTodos().stream().map(todo -> copyTodo(copiedRoutineVO, todo))
                .collect(Collectors.toList()));
        return copiedRoutineVO;
    }

    private TodoVO copyTodo(RoutineVO copiedRoutineVO, TodoVO todoVO) {
        TodoVO copiedTodo = new TodoVO();
        copiedTodo.setTodoName(todoVO.getTodoName());
        copiedTodo.setScariness(todoVO.getScariness());
        copiedTodo.setHardness(todoVO.getHardness());
        copiedTodo.setReady(false);
        copiedTodo.setRoutine(copiedRoutineVO);
        copiedTodo.setBlock(copiedRoutineVO.getBlock());
        copiedTodo = todoService.save(copiedRoutineVO.getBlock().getId(), copiedTodo);
        return copiedTodo;
    }

    private void mapTodosFromRoutinesToBlock(BlockVO blockVO) {
        List<TodoVO> todoVOs = new ArrayList<>();
        blockVO.getRoutines().forEach(routineVO -> todoVOs.addAll(routineVO.getTodos()));
        todoVOs.forEach(todoVO -> todoVO.setBlock(blockVO));
        blockVO.setTodos(todoVOs);
    }
}
