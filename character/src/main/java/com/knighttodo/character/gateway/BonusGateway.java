package com.knighttodo.character.gateway;

import com.knighttodo.character.domain.BonusVO;

import java.util.List;
import java.util.Optional;

public interface BonusGateway {

    BonusVO save(BonusVO bonusVO);

    List<BonusVO> findAll();

    Optional<BonusVO> findById(String bonusId);

    void deleteById(String bonusId);
}
