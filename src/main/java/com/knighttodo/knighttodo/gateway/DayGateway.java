package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayGateway {

    DayVO save(DayVO dayVO);

    List<DayVO> findAll();

    Optional<DayVO> findById(UUID dayId);

    void deleteById(UUID dayId);
}
