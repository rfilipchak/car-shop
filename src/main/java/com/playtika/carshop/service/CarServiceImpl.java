package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.CarDao;
import com.playtika.carshop.dao.CarShopDao;
import com.playtika.carshop.dao.PersonDao;
import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CarServiceImpl implements CarService {

    private final Converter converter;
    private final CarDao carDao;
    private final CarShopDao carShopDao;
    private final PersonDao personDao;

//    private final AuthorService authorService;

    @Override
    public long addCar(Car car, int price, String contact) {
        CarShopEntity carShopEntity = new CarShopEntity(checkCarForExist(car),
                price, checkPersonForExsist(contact));
        return carShopDao.save(carShopEntity).getId();
    }

    @Override
    public Collection<CarSaleInfo> getCars() {
        return converter.carShopEntitiesToCarSaleInfoList(carShopDao.findAll());
    }

    @Override
    public Optional<CarSaleInfo> getCar(long id) {
        CarShopEntity carShopEntity = carShopDao.findOne(id);
        if (carShopEntity != null) {
            return Optional.of(converter.carShopEntityToCarSaleInfo(carShopEntity));
        }
        return Optional.empty();
    }

    @Override
    public boolean removeCar(long id) {
        if (carShopDao.exists(id)) {
            carShopDao.delete(id);
            return true;
        }
        return false;
    }

    private CarEntity checkCarForExist(Car car) {
        Optional<CarEntity> existCar = carDao.getCarByRegistration(car.getRegistration());
        if (existCar.isPresent()) {
            return existCar.get();
        }
        CarEntity carEntity = converter.domainToCarEntity(car);
        carDao.save(carEntity);
        return carEntity;
    }

    private PersonEntity checkPersonForExsist(String contact) {
        Optional<PersonEntity> existPerson = personDao.getPersonEntityByContact(contact);
        if (existPerson.isPresent()) {
            return existPerson.get();
        }
        PersonEntity personEntity = converter.domainToPersonEntity(contact);
        personDao.save(personEntity);
        return personEntity;
    }
}
