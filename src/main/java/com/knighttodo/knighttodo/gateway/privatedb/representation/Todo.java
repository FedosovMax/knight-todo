package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "todo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "todo_name")
    private String todoName;

    @Column(name = "scaryness")
    @Enumerated (EnumType.STRING)
    private Scaryness scaryness;

    @Column(name = "hardness")
    @Enumerated (EnumType.STRING)
    private Hardness hardness;

    @Column(name = "ready")
    private boolean ready;

    @ManyToOne
    @JoinColumn(name = "todoBlock")
    @JsonBackReference
    private TodoBlock todoBlock;
}
