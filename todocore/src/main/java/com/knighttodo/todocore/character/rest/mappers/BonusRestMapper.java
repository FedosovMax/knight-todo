package com.knighttodo.todocore.character.rest.mappers;

import com.knighttodo.todocore.character.domain.BonusVO;
import com.knighttodo.todocore.character.rest.request.BonusRequestDto;
import com.knighttodo.todocore.character.rest.response.BonusResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BonusRestMapper {

    BonusVO toBonusVO(BonusRequestDto requestDto);

    @Mapping(target = "id", source = "bonusId")
    BonusVO toBonusVO(String bonusId);

    BonusResponseDto toBonusResponseDto(BonusVO bonusVO);
}
