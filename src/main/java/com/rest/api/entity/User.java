package com.rest.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.rmi.server.UID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long msrl;

    @Column(length = 30, nullable = false)
    private String uid;

    @Column(length = 100, nullable = false)
    private String name;
}
