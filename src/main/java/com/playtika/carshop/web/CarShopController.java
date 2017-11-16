package com.playtika.carshop.web;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.CarSaleRequest;
import com.playtika.carshop.service.CarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class CarShopController {
    private static final Logger LOG
            = LoggerFactory.getLogger(CarShopController.class);


    private final CarService service;

    public CarShopController(CarService service) {
        this.service = service;
    }

    @PostMapping("/carSaleInfo")
    public Long addCarSaleInfo(@RequestBody Car car,
                               @RequestParam long price,
                               @RequestParam String contact) {
        CarSaleRequest carSaleRequest = new CarSaleRequest(car, price, contact);
        long id = service.addCar(carSaleRequest);
        LOG.info("Car sale info was created [carSaleInfo: {};]", carSaleRequest);
        return id;
    }

    @GetMapping("/carSaleInfo")
    public Collection<CarSaleInfo> getAllCars() {
        LOG.info("All cars was requested");
        return service.getCars();
    }

    @GetMapping("/carSaleInfo/{id}")
    public CarSaleInfo getCarInfoById(@PathVariable long id) {
        CarSaleInfo requestedCar = service.getCar(id);
        LOG.info("Car info was requested [Requested id: {}, Car info {};]", id, requestedCar);
        return requestedCar;
    }

    @DeleteMapping("/carSaleInfo/{id}")
    public void removeCarById(@PathVariable long id) {
        service.removeCar(id);
        LOG.info("Car with id was removed [Car id: {};]", id);
    }
}

