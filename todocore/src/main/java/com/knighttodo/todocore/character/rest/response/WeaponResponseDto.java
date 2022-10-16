package com.knighttodo.todocore.character.rest.response;

import com.knighttodo.todocore.character.gateway.privatedb.representation.enums.WeaponType;
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
public class WeaponResponseDto extends ItemResponseDto {

    private WeaponType weaponType;

    private Integer damage;
}
