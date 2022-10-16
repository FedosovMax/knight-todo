package com.knighttodo.todocore.character.service;

import com.knighttodo.todocore.character.domain.BonusVO;

import java.util.List;

public interface BonusService {

    BonusVO save(BonusVO bonusVO);

    List<BonusVO> findAll();

    BonusVO findById(String bonusId);

    BonusVO updateBonus(String bonusId, BonusVO changedBonusVO);

    void deleteById(String bonusId);
}
