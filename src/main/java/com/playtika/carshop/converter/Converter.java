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
        return new CarEntity(car.getBrand(),
                car.getYear(), car.getRegistration(), car.getColor());
    }

    public PersonEntity domainToPersonEntity(String contact) {
        return new PersonEntity(contact);
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