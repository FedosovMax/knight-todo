package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.exception.BonusNotFoundException;
import com.knighttodo.todocore.character.service.BonusService;
import com.knighttodo.todocore.character.service.privatedb.mapper.BonusMapper;
import com.knighttodo.todocore.character.service.privatedb.repository.BonusRepository;
import com.knighttodo.todocore.character.service.privatedb.representation.Bonus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BonusServiceImpl implements BonusService {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;

    @Override
    public BonusVO save(BonusVO bonusVO) {
        Bonus bonus = bonusRepository.save(bonusMapper.toBonus(bonusVO));
        return bonusMapper.toBonusVO(bonus);
    }

    @Override
    public List<BonusVO> findAll() {
        return bonusRepository.findAll().stream().map(bonusMapper::toBonusVO).collect(Collectors.toList());
    }

    @Override
    public BonusVO findById(String bonusId) {
        return bonusRepository.findById(bonusId)
                .map(bonusMapper::toBonusVO)
                .orElseThrow(() -> new BonusNotFoundException(
                        String.format("Bonus with such id:%s can't be found", bonusId)));
    }

    @Override
    public BonusVO updateBonus(String bonusId, BonusVO changedBonusVO) {
        BonusVO bonusVO = findById(bonusId);

        bonusVO.setName(changedBonusVO.getName());
        bonusVO.setRarity(changedBonusVO.getRarity());
        bonusVO.setDamageBoost(changedBonusVO.getDamageBoost());
        bonusVO.setCritChanceBoost(changedBonusVO.getCritChanceBoost());
        bonusVO.setCritDamageBoost(changedBonusVO.getCritDamageBoost());
        bonusVO.setSkillBoost(changedBonusVO.getSkillBoost());

        return bonusMapper.toBonusVO(bonusRepository.save(bonusMapper.toBonus(bonusVO)));
    }

    @Override
    public void deleteById(String bonusId) {
        bonusRepository.deleteById(bonusId);
    }
}
