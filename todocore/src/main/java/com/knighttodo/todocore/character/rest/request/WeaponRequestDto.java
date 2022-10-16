package com.knighttodo.todocore.character.rest.request;

import com.knighttodo.todocore.character.gateway.privatedb.representation.enums.WeaponType;
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
public class WeaponRequestDto extends ItemRequestDto {

    @ValidEnumValue(enumClass = WeaponType.class)
    private String weaponType;

    @NotNull
    private Integer damage;
}
