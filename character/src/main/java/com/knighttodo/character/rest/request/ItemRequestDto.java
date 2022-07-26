package com.knighttodo.character.rest.request;

import com.knighttodo.character.gateway.privatedb.representation.enums.Rarity;
import com.knighttodo.character.validation.annotation.ValidEnumValue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class ItemRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Integer requiredLevel;

    @NotNull
    private Integer requiredStrength;

    @NotNull
    private Integer requiredAgility;

    @NotNull
    private Integer requiredIntelligence;

    @ValidEnumValue(enumClass = Rarity.class)
    private String rarity;

    @NotNull
    private List<String> bonusIds = new ArrayList<>();
}
