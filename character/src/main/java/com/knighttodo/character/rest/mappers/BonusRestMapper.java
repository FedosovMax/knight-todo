package com.knighttodo.character.rest.mappers;

import com.knighttodo.character.domain.BonusVO;
import com.knighttodo.character.rest.request.BonusRequestDto;
import com.knighttodo.character.rest.response.BonusResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BonusRestMapper {

    BonusVO toBonusVO(BonusRequestDto requestDto);

    @Mapping(target = "id", source = "bonusId")
    BonusVO toBonusVO(String bonusId);

    BonusResponseDto toBonusResponseDto(BonusVO bonusVO);
}
