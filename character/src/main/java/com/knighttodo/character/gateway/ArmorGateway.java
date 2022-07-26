package com.knighttodo.character.gateway;

import com.knighttodo.character.domain.ArmorVO;

import java.util.List;
import java.util.Optional;

public interface ArmorGateway {

    ArmorVO save(ArmorVO armorVO);

    List<ArmorVO> findAll();

    Optional<ArmorVO> findById(String armorId);

    void deleteById(String armorId);
}
