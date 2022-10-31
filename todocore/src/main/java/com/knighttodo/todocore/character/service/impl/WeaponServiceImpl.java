package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.domain.WeaponVO;
import com.knighttodo.todocore.character.exception.WeaponNotFoundException;
import com.knighttodo.todocore.character.service.BonusService;
import com.knighttodo.todocore.character.service.WeaponService;
import com.knighttodo.todocore.character.service.privatedb.mapper.WeaponMapper;
import com.knighttodo.todocore.character.service.privatedb.repository.WeaponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WeaponServiceImpl implements WeaponService {

    private final WeaponRepository weaponRepository;
    private final WeaponMapper weaponMapper;
    private final BonusService bonusService;

    @Override
    public WeaponVO save(WeaponVO weaponVO) {
        weaponVO.setBonuses(fetchBonusesForWeapon(weaponVO));
        return weaponMapper.toWeaponVO(weaponRepository.save(weaponMapper.toWeapon(weaponVO)));
    }

    private List<BonusVO> fetchBonusesForWeapon(WeaponVO weaponVO) {
        return weaponVO.getBonuses().stream()
            .map(BonusVO::getId)
            .map(bonusService::findById)
            .collect(Collectors.toList());
    }

    @Override
    public List<WeaponVO> findAll() {

        return weaponRepository.findAll().stream().map(weaponMapper::toWeaponVO).collect(Collectors.toList());
    }

    @Override
    public WeaponVO findById(String weaponId) {
        return weaponRepository.findById(weaponId).map(weaponMapper::toWeaponVO).orElseThrow(
            () -> new WeaponNotFoundException(String.format("Weapon with such id:%s can't be found", weaponId)));
    }

    @Override
    public WeaponVO updateWeapon(String weaponId, WeaponVO changedWeaponVO) {
        WeaponVO weaponVO = findById(weaponId);

        weaponVO.setDamage(changedWeaponVO.getDamage());
        weaponVO.setWeaponType(changedWeaponVO.getWeaponType());
        weaponVO.setName(changedWeaponVO.getName());
        weaponVO.setDescription(changedWeaponVO.getDescription());
        weaponVO.setRarity(changedWeaponVO.getRarity());
        weaponVO.setRequiredAgility(changedWeaponVO.getRequiredAgility());
        weaponVO.setRequiredIntelligence(changedWeaponVO.getRequiredIntelligence());
        weaponVO.setRequiredLevel(changedWeaponVO.getRequiredLevel());
        weaponVO.setRequiredStrength(changedWeaponVO.getRequiredStrength());
        weaponVO.setBonuses(fetchBonusesForWeapon(changedWeaponVO));

        return weaponMapper.toWeaponVO(weaponRepository.save(weaponMapper.toWeapon(weaponVO)));
    }

    @Override
    public void deleteById(String weaponId) {weaponRepository.deleteById(weaponId);
    }
}
