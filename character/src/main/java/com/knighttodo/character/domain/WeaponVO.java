package com.knighttodo.character.domain;

import com.knighttodo.character.gateway.privatedb.representation.enums.WeaponType;

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
public class WeaponVO extends ItemVO {

    private WeaponType weaponType;

    private Integer damage;
}
