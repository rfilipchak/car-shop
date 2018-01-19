package com.playtika.carshop.carshopservice;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;

import java.util.Collection;
import java.util.Optional;

public interface CarService {

    long addCar(Car car, int price, String contact);

    Collection<CarSaleInfo> getCars();

    Optional<CarSaleInfo> getCar(long id);

    boolean removeCar(long id);
}
