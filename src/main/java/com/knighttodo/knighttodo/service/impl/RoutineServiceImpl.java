package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineGateway;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.TodoBlockService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoutineServiceImpl implements RoutineService {

    private final RoutineGateway routineGateway;
    private TodoBlockService todoBlockService;

    @Autowired
    public void setTodoBlockService(TodoBlockService todoBlockService) {
        this.todoBlockService = todoBlockService;
    }

    @Override
    public RoutineVO save(String blockId, RoutineVO routineVO) {
        routineVO.setTodoBlock(todoBlockService.findById(blockId));
        RoutineVO dbRoutineVO = routineGateway.save(routineVO);
        dbRoutineVO.setTemplateId(dbRoutineVO.getId());
        dbRoutineVO = routineGateway.save(dbRoutineVO);
        return dbRoutineVO;
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
        routineVO.setTodoBlock(todoBlockService.findById(blockId));
        routineVO.setName(changedRoutineVO.getName());
        routineVO.setTemplateId(routineId);
        routineVO.setHardness(changedRoutineVO.getHardness());
        routineVO.setScariness(changedRoutineVO.getScariness());
        routineVO.setReady(changedRoutineVO.isReady());
        return routineGateway.save(routineVO);
    }

    @Override
    public void deleteById(String routineId) {
        routineGateway.deleteById(routineId);
    }

    @Override
    public List<RoutineVO> findAllPatterns() {
        return routineGateway.findAllTemplates();
    }
}
