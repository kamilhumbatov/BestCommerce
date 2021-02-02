package com.commerce.models;

import com.commerce.common.enums.RoleName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private RoleName name;
}
