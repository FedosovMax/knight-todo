package com.knighttodo.todocore.character.rest.request;

import com.knighttodo.todocore.character.gateway.privatedb.representation.enums.ArmorType;
import com.knighttodo.todocore.character.validation.annotation.ValidEnumValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ArmorRequestDto extends ItemRequestDto {

    @ValidEnumValue(enumClass = ArmorType.class)
    private String armorType;

    @NotNull
    private Integer defence;
}
