package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.DayVO;
import com.knighttodo.todocore.exception.DayNotFoundException;
import com.knighttodo.todocore.service.privatedb.mapper.DayMapper;
import com.knighttodo.todocore.service.privatedb.repository.DayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class DayService {

    private final DayRepository dayRepository;
    private final DayMapper dayMapper;

    @Transactional
    public DayVO save(DayVO dayVO) {
        return dayMapper.toDayVO(dayRepository.save(dayMapper.toDay(dayVO)));
    }

    public List<DayVO> findAll() {
        return dayRepository.findAllAlive().stream().map(dayMapper::toDayVO).collect(Collectors.toList());
    }

    public DayVO findById(UUID dayId) {
        return dayRepository.findByIdAlive(dayId).map(dayMapper::toDayVO).orElseThrow(() -> {
            log.error(String.format("Day with such id:%s can't be " + "found", dayId));
            return new DayNotFoundException(String.format("Day with such id:%s can't be " + "found", dayId));
        });
    }

    @Transactional
    public DayVO updateDay(UUID dayId, DayVO changedDayVO) {
        DayVO dayVO = findById(dayId);
        dayVO.setDayName(changedDayVO.getDayName());
        return dayMapper.toDayVO(dayRepository.save(dayMapper.toDay(dayVO)));
    }

    @Transactional
    public void deleteById(UUID dayId) {
        dayRepository.softDeleteAllDayTodosByDayId(dayId);
        dayRepository.softDeleteById(dayId);
    }
}
