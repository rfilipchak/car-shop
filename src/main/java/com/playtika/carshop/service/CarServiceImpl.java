package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.repository.CarRepository;
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

    private final CarRepository carRepository;
    private final Converter converter;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Converter converter) {
        this.carRepository = carRepository;
        this.converter = converter;
    }

    @Override
    public long addCar(Car car, int price, String contact) {
        return carRepository.addCarSaleInfo(converter.domainToCarEntity(car),
                converter.domainToPersonEntity(contact), price);
    }

    @Override
    public Collection<CarSaleInfo> getCars() {
        return converter.carShopEntitiesToCarSaleInfoList(carRepository.getCars());
    }

    @Override
    public Optional<CarSaleInfo> getCar(long id) {
        CarShopEntity carShopEntity = carRepository.getCar(id);
        if(carShopEntity != null) {
            return Optional.of(converter.carShopEntityToCarSaleInfo(carShopEntity));
        }
        return Optional.empty();
    }

    @Override
    public boolean removeCar(long id) {
        return carRepository.removeCar(id);
    }
}
