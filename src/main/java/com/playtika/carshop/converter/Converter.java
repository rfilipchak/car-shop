package com.playtika.carshop.converter;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
@Component
@NoArgsConstructor
public class Converter {

    public CarEntity domainToCarEntity(Car car) {
        CarEntity carEntity = new CarEntity();
        carEntity.setBrand(car.getBrand());
        carEntity.setYear(car.getYear());
        carEntity.setRegistration(car.getRegistration());
        carEntity.setColor(car.getColor());
        return carEntity;
    }

    public PersonEntity domainToPersonEntity(String contact) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setContact(contact);
        return personEntity;
    }

    public CarSaleInfo carShopEntityToCarSaleInfo(CarShopEntity car) {
        return new CarSaleInfo(car.getId(),
                new Car(car.getCar().getBrand(), car.getCar().getYear()
                        , car.getCar().getRegistration(), car.getCar().getColor())
                , car.getPrice(), car.getPerson().getContact());
    }

    public Collection<CarSaleInfo> carShopEntitiesToCarSaleInfoList(Collection<CarShopEntity> list) {
        Collection<CarSaleInfo> carSaleInfos = new ArrayList<>();
        for (CarShopEntity carShopEntity : list) {
            carSaleInfos.add(carShopEntityToCarSaleInfo(carShopEntity));
        }
        return carSaleInfos;
    }
}