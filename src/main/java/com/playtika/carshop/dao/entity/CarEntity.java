package com.playtika.carshop.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String registration;
    private String brand;
    private String color;

    @Column(name = "car_year")
    private int year;

    public CarEntity(String registration, String brand, int year, String color) {
        this.registration = registration;
        this.brand = brand;
        this.year = year;
        this.color = color;
    }
}