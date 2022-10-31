package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.ArmorVO;
import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.exception.ArmorNotFoundException;
import com.knighttodo.todocore.character.service.ArmorService;
import com.knighttodo.todocore.character.service.BonusService;
import com.knighttodo.todocore.character.service.privatedb.mapper.ArmorMapper;
import com.knighttodo.todocore.character.service.privatedb.repository.ArmorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArmorServiceImpl implements ArmorService {

    private final BonusService bonusService;
    private final ArmorRepository armorRepository;
    private final ArmorMapper armorMapper;

    @Override
    public ArmorVO save(ArmorVO armorVO) {
        armorVO.setBonuses(fetchBonusesForArmor(armorVO));
        return armorMapper.toArmorVO(armorRepository.save(armorMapper.toArmor(armorVO)));
    }

    private List<BonusVO> fetchBonusesForArmor(ArmorVO armorVO) {
        return armorVO.getBonuses().stream().map(BonusVO::getId).map(bonusService::findById).collect(Collectors.toList());
    }

    @Override
    public List<ArmorVO> findAll() {
        return armorRepository.findAll().stream().map(armorMapper::toArmorVO).collect(Collectors.toList());
    }

    @Override
    public ArmorVO findById(String armorId) {
        return armorRepository.findById(armorId).map(armorMapper::toArmorVO).orElseThrow(() -> new ArmorNotFoundException(String.format("Armor with such id:%s can't be found", armorId)));
    }

    @Override
    public ArmorVO updateArmor(String armorId, ArmorVO changedArmorVO) {
        ArmorVO armorVO = findById(armorId);

        armorVO.setDefence(changedArmorVO.getDefence());
        armorVO.setArmorType(changedArmorVO.getArmorType());
        armorVO.setName(changedArmorVO.getName());
        armorVO.setDescription(changedArmorVO.getDescription());
        armorVO.setRarity(changedArmorVO.getRarity());
        armorVO.setRequiredAgility(changedArmorVO.getRequiredAgility());
        armorVO.setRequiredIntelligence(changedArmorVO.getRequiredIntelligence());
        armorVO.setRequiredLevel(changedArmorVO.getRequiredLevel());
        armorVO.setRequiredStrength(changedArmorVO.getRequiredStrength());
        armorVO.setBonuses(fetchBonusesForArmor(changedArmorVO));
        return armorMapper.toArmorVO(armorRepository.save(armorMapper.toArmor(armorVO)));
    }

    @Override
    public void deleteById(String armorId) {
        armorRepository.deleteById(armorId);
    }
}
