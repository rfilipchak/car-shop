package com.playtika.carshop.dao.repository;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;

import java.util.Collection;

public interface CarRepository {
    long addCarSaleInfo(CarEntity c, PersonEntity p, int price);

    Collection<CarShopEntity> getCars();

    CarShopEntity getCar(long id);

    boolean removeCar(long id);
}
