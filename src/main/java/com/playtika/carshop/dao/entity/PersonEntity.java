package com.playtika.carshop.dao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "person")
@NoArgsConstructor
@Getter
@Setter
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String contact;

    public PersonEntity(String contact) {
        this.contact = contact;
    }
}