package com.knighttodo.character.gateway;

import com.knighttodo.character.domain.WeaponVO;
import com.knighttodo.character.gateway.privatedb.mapper.WeaponMapper;
import com.knighttodo.character.gateway.privatedb.repository.WeaponRepository;
import com.knighttodo.character.gateway.privatedb.representation.Weapon;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class WeaponGatewayImpl implements WeaponGateway {

    private final WeaponRepository weaponRepository;
    private final WeaponMapper weaponMapper;

    @Override
    public WeaponVO save(WeaponVO weaponVO) {
        Weapon weapon = weaponMapper.toWeapon(weaponVO);
        return weaponMapper.toWeaponVO(weaponRepository.save(weapon));
    }

    @Override
    public List<WeaponVO> findAll() {
        return weaponRepository.findAll().stream().map(weaponMapper::toWeaponVO).collect(Collectors.toList());
    }

    @Override
    public Optional<WeaponVO> findById(String weaponId) {
        return weaponRepository.findById(weaponId).map(weaponMapper::toWeaponVO);
    }

    @Override
    public void deleteById(String weaponId) {
        weaponRepository.deleteById(weaponId);
    }
}
