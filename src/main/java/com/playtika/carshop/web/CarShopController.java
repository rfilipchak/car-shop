package com.playtika.carshop.web;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.exeptions.CarNotFoundException;
import com.playtika.carshop.service.CarService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cars")
@Data
public class CarShopController {
    private static final Logger LOG
            = LoggerFactory.getLogger(CarShopController.class);

    private final CarService service;

    @PostMapping
    public Long addCarSaleInfo(@RequestBody Car car,
                               @RequestParam long price,
                               @RequestParam String contact) {
        long id = service.addCar(car, price, contact);
        LOG.info("Car sale info was created [carSaleInfo: id: {},{},price: {}, contact: {};]", id, car, price, contact);
        return id;
    }

    @GetMapping
    public Collection<CarSaleInfo> getAllCars() {
        LOG.info("All cars was requested");
        return service.getCars();
    }

    @GetMapping(value = "{id}")
    public CarSaleInfo getCarInfoById(@PathVariable("id") long id) throws CarNotFoundException {
        return service.getCar(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found", id));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<HttpStatus> removeCarById(@PathVariable("id") long id) {
        if (service.removeCar(id)) {
            LOG.info("Car with id was removed [Car id: {};]", id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            LOG.warn("Car with id not exist or just removed[Car id: {};]", id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }
}

