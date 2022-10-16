package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.BonusVO;

import java.util.List;
import java.util.Optional;

public interface BonusGateway {

    BonusVO save(BonusVO bonusVO);

    List<BonusVO> findAll();

    Optional<BonusVO> findById(String bonusId);

    void deleteById(String bonusId);
}
