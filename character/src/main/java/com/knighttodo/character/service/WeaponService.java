package com.knighttodo.character.service;

import com.knighttodo.character.domain.WeaponVO;

import java.util.List;

public interface WeaponService {

    WeaponVO save(WeaponVO weaponVO);

    List<WeaponVO> findAll();

    WeaponVO findById(String weaponId);

    WeaponVO updateWeapon(String weaponId, WeaponVO changedWeaponVO);

    void deleteById(String weaponId);
}
