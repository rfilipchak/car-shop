package com.playtika.carshop.service;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.dao.repository.CarRepJpa;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
        Converter c = new Converter();
        if (car != null) {
            return c.carShopEntityToCarSaleInfo(car);
        } else {
            return null;
        }
    }

    @Override
    public boolean removeCar(Long id) {
        return carsCarRepJpa.removeCar(id);
    }

    @Override
    public Collection<Car> getAllCars() {
        return carsCarRepJpa.getAllCars();
    }

    @NoArgsConstructor
    class Converter {
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

        private CarEntity domainToCarEntity(Car car) {
            CarEntity carEntity = new CarEntity();
            carEntity.setBrand(car.getBrand());
            carEntity.setYear(car.getYear());
            carEntity.setRegistration(car.getRegistration());
            carEntity.setColor(car.getColor());
            return carEntity;
        }

        private PersonEntity domainToPersonEntity(String contact) {
            PersonEntity personEntity = new PersonEntity();
            personEntity.setContact(contact);
            return personEntity;
        }

        private CarSaleInfo carShopEntityToCarSaleInfo(CarShopEntity car) {
            return new CarSaleInfo(car.getId(),
                    new Car(car.getCar().getBrand(), car.getCar().getYear()
                            , car.getCar().getRegistration(), car.getCar().getColor())
                    , car.getPrice(), car.getPerson().getContact());
        }
    }
}
