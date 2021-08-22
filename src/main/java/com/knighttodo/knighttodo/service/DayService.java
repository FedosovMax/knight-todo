package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.DayVO;

import java.util.List;
import java.util.UUID;

public interface DayService {

    DayVO save(DayVO dayVO);

    List<DayVO> findAll();

    DayVO findById(UUID dayId);

    DayVO updateDay(UUID dayId, DayVO changedDayVO);

    void deleteById(UUID dayId);
}
