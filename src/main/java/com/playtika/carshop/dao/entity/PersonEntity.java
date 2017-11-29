package com.playtika.carshop.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "person")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private  String contact;
}
