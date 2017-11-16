package com.playtika.carshop.service;

import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.CarSaleRequest;

import java.util.Collection;

public interface CarService {
    long addCar(CarSaleRequest carSaleRequest);
    Collection getCars();
    CarSaleInfo getCar(Long id);
    void removeCar(Long id);

}
