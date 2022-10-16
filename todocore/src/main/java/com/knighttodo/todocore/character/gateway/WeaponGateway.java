package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.WeaponVO;

import java.util.List;
import java.util.Optional;

public interface WeaponGateway {

    WeaponVO save(WeaponVO weaponVO);

    List<WeaponVO> findAll();

    Optional<WeaponVO> findById(String weaponId);

    void deleteById(String weaponId);
}
