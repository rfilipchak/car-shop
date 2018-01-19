package com.playtika.carshop.web;

import com.playtika.carshop.dealservice.DealService;
import com.playtika.carshop.dealstatus.DealStatus;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.Deal;
import com.playtika.carshop.exeptions.CarNotFoundException;
import com.playtika.carshop.carshopservice.CarService;
import com.playtika.carshop.exeptions.DealNotFoundExeption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/cars")
@Slf4j
@Data
public class CarShopController {

    private final CarService carService;
    private final DealService dealService;

    @PostMapping
    public Long addCarSaleInfo(@RequestBody Car car,
                               @RequestParam int price,
                               @RequestParam String contact) {
        long id = carService.addCar(car, price, contact);
        log.info("Car sale info was created [carSaleInfo: id: {},{},price: {}, contact: {};]", id, car, price, contact);
        return id;
    }

    //Create Tests
    @PostMapping ("/deal")
    public Long addDeal(@RequestParam String buyer,
                        @RequestParam int price,
                        @RequestParam long carSaleId) {
        long id = dealService.addDeal(getCarInfoById(carSaleId), buyer, price);
        log.info("Car sale info was created [carSaleInfo: id: {},{},price: {}, contact: {};]", id, buyer, price);
        return id;
    }


    //Create Tests
    @PostMapping//????????????????/ not correct
    public String autoAcceptBestDealOfCar(@RequestParam long carId) {
        long id = getBestDeal(carId).getId();
        // long id = getCarInfoById(carId).getId();
        DealStatus status = dealService.getDealStatusById(id)//NOT VALID ID
                .orElseThrow(() -> new DealNotFoundExeption("Deal not found for CarSaleId", carId));
        if (status.equals(DealStatus.ACCEPTED)) {
            log.info("Deal for CarSaleId just ACCEPTED [Deal id: {};]", id);
            return status.toString();
        } else {
            dealService.acceptDeal(id);
            log.info("Deal for CarSaleId was ACCEPTED [Deal id: {};]", id);
            return status.toString();
        }
    }

    @GetMapping
    public Collection<CarSaleInfo> getAllCars() {
        log.info("All cars was requested");
        return carService.getCars();
    }

    //Create Tests
    @GetMapping("/deal")
    public Collection<Deal> getAllDeals() {
        log.info("All deals was requested");
        return dealService.getAllDeals();
    }

    //Create Tests
    @GetMapping("/deal/getAllDealsTheSameCar/{carId}")//@PathVariable("carId")
    public Collection<Deal> getAllDealsTheSameCar(@PathVariable("carId") long carId) throws DealNotFoundExeption {
        log.info("All deals by carId was requested");
        return dealService.getAllDealsTheSameCar(getCarInfoById(carId).getId());
    }

    @GetMapping(value = "{id}")
    public CarSaleInfo getCarInfoById(@PathVariable("id") long id) throws CarNotFoundException {
        return carService.getCar(id)
                .orElseThrow(() -> new CarNotFoundException("Car not found", id));
    }

    // Create tests
    @GetMapping("/deal/{carId}")
    public Deal getBestDeal(@PathVariable("carId") long carId) throws DealNotFoundExeption {
        return dealService.getBestDeal(getCarInfoById(carId).getId())
                .orElseThrow(() -> new DealNotFoundExeption("Deal not found for CarSaleId", carId));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<HttpStatus> removeCarById(@PathVariable("id") long id) {
        dealService.removeDealByCarShopId(id);
        if (carService.removeCar(id)) {
            log.info("Car with id was removed [Car id: {};]", id);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            log.warn("Car with id not exist or just removed[Car id: {};]", id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

}

