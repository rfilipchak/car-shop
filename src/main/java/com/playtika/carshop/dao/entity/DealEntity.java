package com.playtika.carshop.dao.entity;

import com.playtika.carshop.dao.entity.status.CarStatus;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "deals")
@Data
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    @Column(name = "buyer_price")
    private long buyerPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CarStatus status = CarStatus.Active;

    @ManyToOne
    @JoinColumn(name = "car_for_sale_id")
    private CarShopEntity carShopEntity;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;


}
