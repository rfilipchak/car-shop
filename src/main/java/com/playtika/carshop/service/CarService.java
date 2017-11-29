package com.playtika.carshop.service;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;

import java.util.Collection;
import java.util.Optional;

public interface CarService {

    long addCar(Car car, long price, String contact);

    Collection<CarSaleInfo> getCars();

    Optional<CarSaleInfo> getCar(Long id);

    boolean removeCar(Long id);

    Collection<Car> getAllCars();//for test

}
