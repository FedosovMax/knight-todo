package com.knighttodo.character.gateway;

import com.knighttodo.character.domain.ItemVO;

import java.util.List;

public interface ItemGateway {

    List<ItemVO> findAll();
}
