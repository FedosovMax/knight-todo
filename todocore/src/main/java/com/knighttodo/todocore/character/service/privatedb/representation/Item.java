package com.knighttodo.todocore.character.service.privatedb.representation;

import com.knighttodo.todocore.character.service.privatedb.representation.enums.Rarity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@Data
@NoArgsConstructor
public abstract class Item {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    private String name;

    private String description;

    @Column(name = "required_level")
    private Integer requiredLevel;

    @Column(name = "required_strength")
    private Integer requiredStrength;

    @Column(name = "required_agility")
    private Integer requiredAgility;

    @Column(name = "required_intelligence")
    private Integer requiredIntelligence;

    @Enumerated(EnumType.STRING)
    private Rarity rarity;

    @ManyToMany
    @JoinTable(
        name = "bonus_item",
        joinColumns = {@JoinColumn(name = "item_id")},
        inverseJoinColumns = {@JoinColumn(name = "bonus_id")}
    )
    private List<Bonus> bonuses = new ArrayList<>();
}
