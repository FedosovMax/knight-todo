package com.knighttodo.character.service;

import com.knighttodo.character.domain.ArmorVO;

import java.util.List;

public interface ArmorService {

    ArmorVO save(ArmorVO armorVO);

    List<ArmorVO> findAll();

    ArmorVO findById(String armorId);

    ArmorVO updateArmor(String armorId, ArmorVO changedArmorVO);

    void deleteById(String armorId);
}
