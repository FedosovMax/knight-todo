package com.knighttodo.todocore.gateway.privatedb.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "todo_role")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}
