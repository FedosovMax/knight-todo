package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.DayVO;

import java.util.List;

public interface DayService {

    DayVO save(DayVO dayVO);

    List<DayVO> findAll();

    DayVO findById(String dayId);

    DayVO updateDay(String dayId, DayVO changedDayVO);

    void deleteById(String dayId);
}
