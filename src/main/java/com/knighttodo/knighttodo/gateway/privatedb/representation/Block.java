package com.knighttodo.knighttodo.gateway.privatedb.representation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "block")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Block {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private String id;

    @Column(name = "block_name")
    private String blockName;

    @OneToMany(mappedBy = "block", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Routine> routines = new ArrayList<>();

    @OneToMany(mappedBy = "block", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();
}
