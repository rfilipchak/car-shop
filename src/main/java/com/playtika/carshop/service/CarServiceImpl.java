package com.playtika.carshop.service;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CarServiceImpl implements CarService {
    private final Map<Long, CarSaleInfo> carSaleInfoMap =
            new ConcurrentHashMap<>();
    private final AtomicLong idGen = new AtomicLong();

    @Override
    public long addCar(Car car, long price, String contact) {
        long id = idGen.incrementAndGet();
        CarSaleInfo carSaleInfo = new CarSaleInfo(id, car, price, contact);
        carSaleInfoMap.put(carSaleInfo.getId(), carSaleInfo);
        return carSaleInfo.getId();
    }

    @Override
    public Collection<CarSaleInfo> getCars() {
        return carSaleInfoMap.values();
    }

    @Override
    public Optional<CarSaleInfo> getCar(Long id) {
        return Optional.ofNullable(carSaleInfoMap.get(id));
    }

    @Override
    public boolean removeCar(Long id) {
        if (carSaleInfoMap.containsKey(id)) {
            carSaleInfoMap.remove(id);
            return true;
        } else return false;
    }

}
