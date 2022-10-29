package com.knighttodo.todocore.character.gateway.privatedb.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skill")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id")
    private String id;

    private String name;

    private String description;

    @ManyToMany
    @JoinTable(
        name = "bonus_skill",
        joinColumns = {@JoinColumn(name = "skill_id")},
        inverseJoinColumns = {@JoinColumn(name = "bonus_id")}
    )
    private List<Bonus> bonuses = new ArrayList<>();
}
