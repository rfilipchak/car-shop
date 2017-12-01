package com.playtika.carshop.converter;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
public  class Converter {
    @NonNull
    private Car car;
    @NonNull
    private long price;
    @NonNull
    private String contact;

    private CarShopEntity domeinToCarShopEntity(Car car, long price, String contact) {

        CarShopEntity carShopEntity = new CarShopEntity();
        carShopEntity.setCar(domainToCarEntity(car));
        carShopEntity.setPerson(domainToPersonEntity(contact));
        carShopEntity.setPrice(price);
        return carShopEntity;
    }

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
        if (car != null) {
        return new CarSaleInfo(car.getId(),
                new Car(car.getCar().getBrand(), car.getCar().getYear()
                        , car.getCar().getRegistration(), car.getCar().getColor())
                , car.getPrice(), car.getPerson().getContact());
        } else {
            return null;
        }
    }
}