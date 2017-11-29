package com.playtika.carshop.dao.entity;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "cars_shop")
@Data
public class CarShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long price;

    @OneToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "car_id")
    private CarEntity car;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "person_id")
    private PersonEntity person;

}
