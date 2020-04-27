package com.knighttodo.knighttodo.gateway.privatedb.representation;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "routine")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"block"})
public class Routine {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(
        name = "uuid",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Scariness scariness;

    @Enumerated(EnumType.STRING)
    private Hardness hardness;

    private boolean ready;

    @Column(name = "template_id")
    private String templateId;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;

    @OneToMany(mappedBy = "routine")
    private List<Todo> todos = new ArrayList<>();
}
