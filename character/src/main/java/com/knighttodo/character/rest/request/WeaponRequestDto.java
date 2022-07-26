package com.knighttodo.character.rest.request;

import com.knighttodo.character.gateway.privatedb.representation.enums.WeaponType;
import com.knighttodo.character.validation.annotation.ValidEnumValue;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
