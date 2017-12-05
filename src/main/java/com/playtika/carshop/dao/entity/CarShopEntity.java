package com.playtika.carshop.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cars_shop")
@NoArgsConstructor
@Getter
@Setter
public class CarShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int price;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private CarEntity car;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private PersonEntity person;

    public CarShopEntity(CarEntity car, int price, PersonEntity person) {
        this.price = price;
        this.car = car;
        this.person = person;
    }
}