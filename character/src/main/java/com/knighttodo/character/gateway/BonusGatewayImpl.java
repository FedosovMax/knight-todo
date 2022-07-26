package com.knighttodo.character.gateway;

import com.knighttodo.character.domain.BonusVO;
import com.knighttodo.character.gateway.privatedb.mapper.BonusMapper;
import com.knighttodo.character.gateway.privatedb.repository.BonusRepository;
import com.knighttodo.character.gateway.privatedb.representation.Bonus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BonusGatewayImpl implements BonusGateway {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;

    @Override
    public BonusVO save(BonusVO bonusVO) {
        Bonus bonus = bonusMapper.toBonus(bonusVO);
        return bonusMapper.toBonusVO(bonusRepository.save(bonus));
    }

    @Override
    public List<BonusVO> findAll() {
        return bonusRepository.findAll().stream().map(bonusMapper::toBonusVO).collect(Collectors.toList());
    }

    @Override
    public Optional<BonusVO> findById(String bonusId) {
        return bonusRepository.findById(bonusId).map(bonusMapper::toBonusVO);
    }

    @Override
    public void deleteById(String bonusId) {
        bonusRepository.deleteById(bonusId);
    }
}
