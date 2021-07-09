package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.DayMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.DayRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Day;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DayGatewayImpl implements DayGateway {

    private final DayRepository dayRepository;
    private final DayMapper dayMapper;

    @Override
    public DayVO save(DayVO dayVO) {
        Day savedDay = dayRepository.save(dayMapper.toDay(dayVO));
        return dayMapper.toDayVO(savedDay);
    }

    @Override
    public List<DayVO> findAll() {
        return dayRepository.findAll().stream().map(dayMapper::toDayVO).collect(Collectors.toList());
    }

    @Override
    public Optional<DayVO> findById(UUID dayId) {
        return dayRepository.findById(dayId).map(dayMapper::toDayVO);
    }

    @Override
    public void deleteById(UUID dayId) {
        dayRepository.deleteById(dayId);
    }
}
