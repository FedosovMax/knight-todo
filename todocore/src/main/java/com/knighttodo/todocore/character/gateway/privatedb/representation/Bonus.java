package com.knighttodo.todocore.character.gateway.privatedb.representation;

import com.knighttodo.todocore.character.gateway.privatedb.representation.enums.Rarity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bonus")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Bonus {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @Column(name = "damage_boost")
    private Integer damageBoost;

    @Column(name = "crit_chance_boost")
    private Integer critChanceBoost;

    @Column(name = "crit_damage_boost")
    private Integer critDamageBoost;

    @Column(name = "skill_boost")
    private Integer skillBoost;
}
