package com.springboot.shootformoney.common;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
public class TestEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int a;
}
