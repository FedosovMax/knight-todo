package com.knighttodo.character.service.impl;

import com.knighttodo.character.domain.ItemVO;
import com.knighttodo.character.gateway.ItemGateway;
import com.knighttodo.character.service.ItemService;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemGateway itemGateway;

    @Override
    public List<ItemVO> findAll() {
        return itemGateway.findAll();
    }
}
