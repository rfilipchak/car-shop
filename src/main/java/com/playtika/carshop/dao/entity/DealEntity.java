package com.playtika.carshop.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "deals")
@NoArgsConstructor
@Getter
@Setter
public class DealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long buyerPrice;

    @Enumerated(EnumType.STRING)
    private CarStatus status = CarStatus.ACTIVE;

    @ManyToOne
    @JoinColumn(name = "car_for_sale_id")
    private CarShopEntity carShopEntity;

    @ManyToOne
    private PersonEntity person;


}