package com.playtika.carshop.dao.repository;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;

import java.util.Collection;

public interface CarRepJpa {
    long addCarSaleInfo(CarEntity c, PersonEntity p, Long price);
    Collection<CarSaleInfo> getCars();
    CarShopEntity getCar(Long id);
    boolean removeCar(Long id);

    Collection<Car> getAllCars();
}
