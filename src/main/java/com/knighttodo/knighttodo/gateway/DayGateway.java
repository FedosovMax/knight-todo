package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.DayMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.DayRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Day;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class DayGateway {

    private final DayRepository dayRepository;
    private final DayMapper dayMapper;

    public DayVO save(DayVO dayVO) {
        Day savedDay = dayRepository.save(dayMapper.toDay(dayVO));
        return dayMapper.toDayVO(savedDay);
    }

    public List<DayVO> findAll() {
        return dayRepository.findAllAlive().stream().map(dayMapper::toDayVO).collect(Collectors.toList());
    }

    public Optional<DayVO> findById(UUID dayId) {
        return dayRepository.findByIdAlive(dayId).map(dayMapper::toDayVO);
    }

    public void deleteById(UUID dayId) {
        dayRepository.softDeleteById(dayId);
    }

    public void deleteAllDayTodos(UUID dayId) {
        dayRepository.softDeleteAllDayTodosByDayId(dayId);
    }
}
