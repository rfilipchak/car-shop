package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.repository.CarRepJpa;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;


@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepJpa carsCarRepJpa;

    Converter converter = new Converter();

    @Autowired
    public CarServiceImpl(CarRepJpa carsCarRepJpa) {
        this.carsCarRepJpa = carsCarRepJpa;
    }

    @Override
    public long addCar(Car car, long price, String contact) {
        return carsCarRepJpa.addCarSaleInfo(converter.domainToCarEntity(car),
                converter.domainToPersonEntity(contact), price);
    }

    @Override
    public Collection<CarSaleInfo> getCars() {
        return carsCarRepJpa.getCars();
    }

    @Override
    public Optional<CarSaleInfo> getCar(Long id) {
        return Optional.ofNullable(getCarInfo(carsCarRepJpa.getCar(id)));
    }

    private CarSaleInfo getCarInfo(CarShopEntity car) {
        return converter.carShopEntityToCarSaleInfo(car);
    }

    @Override
    public boolean removeCar(Long id) {
        return carsCarRepJpa.removeCar(id);
    }

    @Override
    public Collection<Car> getAllCars() {
        return carsCarRepJpa.getAllCars();
    }

}
