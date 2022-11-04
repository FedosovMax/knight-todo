package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.exception.RoutineTodoNotFoundException;
import com.knighttodo.todocore.service.character.ExperienceServ;
import com.knighttodo.todocore.service.privatedb.mapper.RoutineTodoInstanceMapper;
import com.knighttodo.todocore.service.privatedb.repository.RoutineTodoInstanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineTodoInstanceService {

    private final RoutineInstanceService routineInstanceService;
    private final ExperienceServ experienceService;
    private final RoutineTodoInstanceRepository routineTodoInstanceRepository;
    private final RoutineTodoInstanceMapper routineTodoInstanceMapper;

    @Transactional
    public RoutineTodoInstanceVO save(UUID routineInstanceId, RoutineTodoInstanceVO routineTodoInstanceVO) {
        routineTodoInstanceVO.setRoutineInstanceVO(routineInstanceService.findById(routineInstanceId));
        RoutineTodoInstanceVO savedRoutineTodoInstance =
                routineTodoInstanceMapper.toRoutineTodoInstanceVO(routineTodoInstanceRepository.save(routineTodoInstanceMapper.toRoutineTodoInstance(routineTodoInstanceVO)));
        savedRoutineTodoInstance.setRoutineInstanceVO(routineTodoInstanceVO.getRoutineInstanceVO());
        return savedRoutineTodoInstance;
    }

    public List<RoutineTodoInstanceVO> findAll() {
        return routineTodoInstanceRepository.findAllAlive().stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO).collect(Collectors.toList());
    }

    @Transactional
    public RoutineTodoInstanceVO findById(UUID routineTodoInstanceId) {
        RoutineTodoInstanceVO routineTodoInstanceVO = findRoutineTodoInstanceVO(routineTodoInstanceId);
        return synchronizeWithRoutineTodoAndReturn(routineTodoInstanceVO);
    }

    private RoutineTodoInstanceVO findRoutineTodoInstanceVO(UUID routineTodoInstanceId) {
        return routineTodoInstanceRepository.findByIdAlive(routineTodoInstanceId).map(routineTodoInstanceMapper::toRoutineTodoInstanceVO)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Todo Instance with such id:%s can't be found", routineTodoInstanceId));
                    return new RoutineTodoNotFoundException(String
                            .format("Routine Todo Instance with such id:%s can't be found", routineTodoInstanceId));
                });
    }

    private RoutineTodoInstanceVO synchronizeWithRoutineTodoAndReturn(RoutineTodoInstanceVO routineTodoInstanceVO) {
        RoutineTodoVO routineTodoVO = routineTodoInstanceVO.getRoutineTodoVO();
        routineTodoInstanceVO.setRoutineTodoInstanceName(routineTodoVO.getRoutineTodoName());
        routineTodoInstanceVO.setHardness(routineTodoVO.getHardness());
        routineTodoInstanceVO.setScariness(routineTodoVO.getScariness());
        routineTodoInstanceMapper.toRoutineTodoInstanceVO(routineTodoInstanceRepository.save(routineTodoInstanceMapper.toRoutineTodoInstance(routineTodoInstanceVO)));
        return routineTodoInstanceVO;
    }

    public List<RoutineTodoInstanceVO> findByRoutineInstanceId(UUID routineId) {
        return routineTodoInstanceRepository.findByRoutineInstanceIdAlive(routineId).stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteById(UUID routineTodoInstanceId) {
        routineTodoInstanceRepository.deleteById(routineTodoInstanceId);
    }

    @Transactional
    public RoutineTodoInstanceVO updateIsReady(UUID routineId, UUID routineTodoId, boolean isReady) {
        RoutineTodoInstanceVO routineTodoInstanceVO = findRoutineTodoInstanceVO(routineTodoId);
        routineTodoInstanceVO.setRoutineInstanceVO(routineInstanceService.findRoutineInstanceVO(routineId));
        routineTodoInstanceVO.setReady(isReady);
        routineTodoInstanceMapper.toRoutineTodoInstanceVO(routineTodoInstanceRepository.save(routineTodoInstanceMapper.toRoutineTodoInstance(routineTodoInstanceVO)));
        return experienceService.calculateExperience(routineTodoInstanceVO);
    }
}
