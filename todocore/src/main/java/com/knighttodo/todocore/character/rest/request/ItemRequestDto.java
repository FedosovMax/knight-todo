package com.knighttodo.todocore.character.rest.request;

import com.knighttodo.todocore.character.service.privatedb.representation.enums.Rarity;
import com.knighttodo.todocore.character.validation.annotation.ValidEnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
