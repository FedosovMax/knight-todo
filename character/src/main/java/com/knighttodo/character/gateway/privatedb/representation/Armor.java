package com.knighttodo.character.gateway.privatedb.representation;

import com.knighttodo.character.gateway.privatedb.representation.enums.ArmorType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "armor")
@Data
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Armor extends Item {

    @Enumerated(EnumType.STRING)
    @Column(name = "armor_type")
    private ArmorType armorType;

    private Integer defence;
}
