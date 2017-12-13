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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private final Converter converter;
    private final CarDao carDao;
    private final CarShopDao carShopDao;
    private final PersonDao personDao;

    @Autowired
    public CarServiceImpl(Converter converter, CarDao carDao, CarShopDao carShopDao, PersonDao personDao) {
        this.converter = converter;
        this.carDao = carDao;
        this.carShopDao = carShopDao;
        this.personDao = personDao;
    }

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
        CarEntity existCar = carDao.getCarEntitiesByRegistration(car.getRegistration());
        if (existCar != null) {
            return existCar;
        }
        CarEntity carEntity = converter.domainToCarEntity(car);
        carDao.save(carEntity);
        return carEntity;
    }

    private PersonEntity checkPersonForExsist(String contact) {
        PersonEntity existPerson = personDao.getPersonEntityByContact(contact);
        if (existPerson != null) {
            return existPerson;
        }
        PersonEntity personEntity = converter.domainToPersonEntity(contact);
        personDao.save(personEntity);
        return personEntity;
    }
}
