package com.knighttodo.character.service;

import com.knighttodo.character.domain.BonusVO;

import java.util.List;

public interface BonusService {

    BonusVO save(BonusVO bonusVO);

    List<BonusVO> findAll();

    BonusVO findById(String bonusId);

    BonusVO updateBonus(String bonusId, BonusVO changedBonusVO);

    void deleteById(String bonusId);
}
