package com.playtika.carshop.service;

import com.playtika.carshop.Exeprions.CarNotFoundException;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.CarSaleRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class CarServiceTest {
    private CarService carService;

    @Before
    public void init() {
        carService = new CarServiceImpl();
    }

    @Test
    public void testCRUDCar() {
        CarSaleRequest carSaleRequest = new CarSaleRequest(new Car("Test Car", 2017), 2000, "contact");

        // create
        Long createdId = carService.addCar(carSaleRequest);
        assertThat(createdId).isNotNull();

        // read
        CarSaleInfo carSaleInfo = carService.getCar(createdId);
        assertThat(carSaleInfo).isNotNull();
        assertThat(carSaleInfo.getCar()).isNotNull();
        assertThat(carSaleInfo.getCar().getType()).isEqualTo(carSaleRequest.getCar().getType());
        assertThat(carSaleInfo.getCar().getYear()).isEqualTo(carSaleRequest.getCar().getYear());
        assertThat(carSaleInfo.getPrice()).isEqualTo(carSaleRequest.getPrice());
        assertThat(carSaleInfo.getContact()).isEqualTo(carSaleRequest.getContact());

        Collection<CarSaleInfo> cars = carService.getCars();
        assertThat(cars).isNotEmpty();
        assertThat(cars.size()).isEqualTo(1);
        assertThat(cars.contains(carSaleRequest));

        // delete
        carService.removeCar(createdId);
        assertThat(carService.getCars()).isEmpty();
    }
    @Test(expected = CarNotFoundException.class)
    public void shouldThrowExceptionDuringGetWhenCarDoesNotExist(){
        carService.getCar(Long.MAX_VALUE);
    }

    @Test(expected = CarNotFoundException.class)
    public void shouldThrowExceptionDuringRemoveCarWhenCarDoesNotExist(){
        carService.removeCar(Long.MAX_VALUE);
    }

}