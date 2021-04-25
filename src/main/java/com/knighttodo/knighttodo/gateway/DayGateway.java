package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayVO;

import java.util.List;
import java.util.Optional;

public interface DayGateway {

    DayVO save(DayVO dayVO);

    List<DayVO> findAll();

    Optional<DayVO> findById(String dayId);

    void deleteById(String dayId);
}
