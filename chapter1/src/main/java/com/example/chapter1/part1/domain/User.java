package com.example.chapter1.part1.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "USERS")
@Setter
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
}
