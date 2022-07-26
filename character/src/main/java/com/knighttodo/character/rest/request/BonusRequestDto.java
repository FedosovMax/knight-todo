package com.knighttodo.character.rest.request;

import com.knighttodo.character.gateway.privatedb.representation.enums.Rarity;
import com.knighttodo.character.validation.annotation.ValidEnumValue;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BonusRequestDto {

    @NotBlank
    private String name;

    @ValidEnumValue(enumClass = Rarity.class)
    private String rarity;

    @NotNull
    private Integer damageBoost;

    @NotNull
    private Integer critChanceBoost;

    @NotNull
    private Integer critDamageBoost;

    @NotNull
    private Integer skillBoost;
}
