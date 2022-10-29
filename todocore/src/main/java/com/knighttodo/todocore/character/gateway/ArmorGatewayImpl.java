package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.ArmorVO;
import com.knighttodo.todocore.character.gateway.privatedb.mapper.ArmorMapper;
import com.knighttodo.todocore.character.gateway.privatedb.repository.ArmorRepository;
import com.knighttodo.todocore.character.gateway.privatedb.representation.Armor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ArmorGatewayImpl implements ArmorGateway {

    private final ArmorRepository armorRepository;
    private final ArmorMapper armorMapper;

    @Override
    public ArmorVO save(ArmorVO armorVO) {
        Armor armor = armorMapper.toArmor(armorVO);
        return armorMapper.toArmorVO(armorRepository.save(armor));
    }

    @Override
    public List<ArmorVO> findAll() {
        return armorRepository.findAll().stream().map(armorMapper::toArmorVO).collect(Collectors.toList());
    }

    @Override
    public Optional<ArmorVO> findById(String armorId) {
        return armorRepository.findById(armorId).map(armorMapper::toArmorVO);
    }

    @Override
    public void deleteById(String armorId) {
        armorRepository.deleteById(armorId);
    }
}
