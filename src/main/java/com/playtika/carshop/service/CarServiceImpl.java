package com.playtika.carshop.service;

import com.playtika.carshop.Exeprions.CarNotFoundException;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.CarSaleRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.lang.String.format;

@Service
public class CarServiceImpl implements CarService {
    private final Map<Long, CarSaleInfo> carSaleInfoMap =
            new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    @Override
    public long addCar(CarSaleRequest carSaleRequest) {
        long id = idGen.incrementAndGet();
        CarSaleInfo carSaleInfo = new CarSaleInfo(id, carSaleRequest.getCar()
                , carSaleRequest.getPrice()
                , carSaleRequest.getContact());
        carSaleInfoMap.put(carSaleInfo.getId(), carSaleInfo);
        return carSaleInfo.getId();
    }

    @Override
    public Collection getCars() {
        return carSaleInfoMap.values();
    }

    @Override
    public CarSaleInfo getCar(Long id) {
        CarSaleInfo carSaleInfo = carSaleInfoMap.get(id);
        if (carSaleInfo == null) {
            throw new CarNotFoundException(format("Car with id %s not found", id));
        }
        return carSaleInfo;
    }

    @Override
    public void removeCar(Long id) {
        CarSaleInfo removedCar = carSaleInfoMap.remove(id);
        if (removedCar == null) {
            throw new CarNotFoundException(format("Car with id %s not found", id));
        }
    }

}
