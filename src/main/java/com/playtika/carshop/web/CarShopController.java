package com.playtika.carshop.web;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class CarShopController {
    private static final Logger LOG
            = LoggerFactory.getLogger(CarShopController.class);

    private final Map<Long, CarSaleInfo> carSaleInfoMap = new HashMap<>();
    private long id = 0;

    @PostMapping("/carSaleInfo")
    public long addCarSaleInfo(@RequestBody Car car,
                               @RequestParam long price,
                               @RequestParam String contact) {
        id++;
        CarSaleInfo carSaleInfo = new CarSaleInfo(car, price, contact);
        carSaleInfoMap.put(id, carSaleInfo);
        LOG.info("Car sale info was created [carSaleInfo: {};]", carSaleInfo);
        return id;
    }

    @GetMapping("/carSaleInfo")
    public Collection<CarSaleInfo> getAllCars() {
        LOG.info("All cars was requested");
        return carSaleInfoMap.values();
    }

    @GetMapping("/getCarInfo/id/{id}")
    public Map<Long, String> getCarInfoById(@PathVariable long id) {
        Map<Long, String> carInfoById = carSaleInfoMap.entrySet().stream()
                .filter(c -> c.getKey().equals(id))
                .map(Map.Entry::getValue)
                .collect(Collectors.toMap(CarSaleInfo::getPrice, CarSaleInfo::getContact));
        LOG.info("Car info was requested [Requested id: {}, Car info {};]", id, carInfoById);
        return carInfoById;
    }

    @DeleteMapping("/carSaleInfo/id/{id}")
    public int removeCarById(@PathVariable long id) {
        if (carSaleInfoMap.containsKey(id)) {
            carSaleInfoMap.remove(id);
            LOG.info("Car with id was removed [Car id: {};]", id);
            return 1;
        } else {
            LOG.warn("No such car with id [Car id: {};]", id);
            return -1;
        }
    }
}

