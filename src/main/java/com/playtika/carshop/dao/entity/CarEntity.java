package com.playtika.carshop.dao.entity;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity (name = "cars")
@Data
@RequiredArgsConstructor
public class CarEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id;
        private String registration;
        private String brand;
        private String color;

        @Column(name = "car_year")
        private int year;
}
