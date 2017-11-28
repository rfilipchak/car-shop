package com.playtika.carshop.service;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.DealsEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.daoService.Dao;
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
//    private final Map<Long, CarSaleInfo> carSaleInfoMap =
//            new ConcurrentHashMap<>();
//    private final AtomicLong idGen = new AtomicLong();


    private final Dao carsDao;

    @Autowired
    public CarServiceImpl(Dao carsDao) {
        this.carsDao = carsDao;
    }

    @Override
    public long addCar(Car car, long price, String contact) {
//        long id = idGen.incrementAndGet();
//        CarSaleInfo carSaleInfo = new CarSaleInfo(id, car, price, contact);
//        carSaleInfoMap.put(carSaleInfo.getId(), carSaleInfo);
//        return carSaleInfo.getId();
//        DealsEntity dealsEntity = new DealsEntity();
//        dealsEntity.setCar();
        CarEntity carEntity = new CarEntity();
        carEntity.setBrand(car.getBrand());
        carEntity.setYear(car.getYear());
        PersonEntity personEntity = new PersonEntity();
        personEntity.setContact(contact);
        DealsEntity dealsEntity = new DealsEntity();
        dealsEntity.setCar(carEntity);
        dealsEntity.setPrice(price);
        dealsEntity.setPerson(personEntity);
        return carsDao.addCarSaleInfo(dealsEntity);
        //return carRepository.addCarDb(car, price, contact);
    }

    @Override
    public Collection<CarSaleInfo> getCars() {

        return carsDao.getCars();
        // return carSaleInfoMap.values();//to map
        //return carRepository.getCarsDb();//jdbc
    }

    @Override
    public Optional<CarSaleInfo> getCar(Long id) {

        return Optional.ofNullable(getCarInfo(carsDao.getCar(id)));
        //return carRepository.getCarDb(id);
    }
    private CarSaleInfo getCarInfo(DealsEntity d) {
        if (d!=null){
        return new CarSaleInfo(d.getId(),
                new Car(d.getCar().getBrand(), d.getCar().getYear())
                , d.getPrice(), d.getPerson().getContact());
        }else {
            return null;
        }
    }

    @Override
    public boolean removeCar(Long id) {
//        CarSaleInfo removed = carSaleInfoMap.remove(id);
//        return removed != null;
        return carsDao.removeCar(id);
        //return carRepository.removeCarDb(id);
    }
}
