package com.knighttodo.todocore.character.gateway;

import com.knighttodo.todocore.character.domain.ItemVO;
import com.knighttodo.todocore.character.gateway.privatedb.mapper.ItemMapper;
import com.knighttodo.todocore.character.gateway.privatedb.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class ItemGatewayImpl implements ItemGateway {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public List<ItemVO> findAll() {
        return itemRepository.findAll().stream().map(itemMapper::toItemVO).collect(Collectors.toList());
    }
}
