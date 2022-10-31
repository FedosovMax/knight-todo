package com.knighttodo.todocore.character.rest.request;

import com.knighttodo.todocore.character.service.privatedb.representation.enums.Rarity;
import com.knighttodo.todocore.character.validation.annotation.ValidEnumValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
