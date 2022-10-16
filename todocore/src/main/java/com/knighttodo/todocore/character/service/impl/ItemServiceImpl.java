package com.knighttodo.todocore.character.service.impl;

import com.knighttodo.todocore.character.domain.ItemVO;
import com.knighttodo.todocore.character.gateway.ItemGateway;
import com.knighttodo.todocore.character.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemGateway itemGateway;

    @Override
    public List<ItemVO> findAll() {
        return itemGateway.findAll();
    }
}
