package com.springboot.shootformoney.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class ConfigEntity {

    @Id
    @Column(name = "code_", length = 45)
    private String code;    // PK

    @Lob
    @Column(name = "value_")
    private String value;   // json 형태의 데이터

}
