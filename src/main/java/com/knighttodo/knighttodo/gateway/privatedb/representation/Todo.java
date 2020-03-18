package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "todo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @ColumnDefault("random_uuid()")
    @Column(name = "id")
    private String id;

    @Column(name = "todo_name")
    private String todoName;

    @Column(name = "scaryness")
    @Enumerated(EnumType.STRING)
    private Scaryness scaryness;

    @Column(name = "hardness")
    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    @Column(name = "ready")
    private boolean ready = false;

    @ManyToOne
    @JoinColumn(name = "block_id")
    @JsonBackReference
    private TodoBlock todoBlock;
}
