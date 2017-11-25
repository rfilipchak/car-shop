package com.playtika.carshop.service;

import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.junit.Test;

import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CarServiceTest {

    private CarService service = new CarServiceImpl();

    Car test_car = new Car("Test Car", 2017);
    long price = 2000;
    String contact = "contact";

    @Test
    public void shouldReturnValidIdAfterAddingCar() {
        Long createdId = service.addCar(test_car, price, contact);
        assertThat(createdId).isNotNull().isEqualTo(1L);
    }

    @Test
    public void shouldCreateNotEqualIdsAfterCreating() {
        assertThat(service.addCar(test_car, price, contact))
                .isNotEqualTo(service.addCar(test_car, price, contact));
    }

    @Test
    public void shoulReturnCarById() {
        addCarsForTest();
        Optional<CarSaleInfo> carSaleInfo = service.getCar(1L);
        assertThat(service.getCar(1L)).isEqualTo(Optional.of(new CarSaleInfo(1L, test_car, price, contact)));
        assertThat(service.getCar(2L)).isEqualTo(Optional.of(new CarSaleInfo(2L, test_car, price, contact)));
    }

    @Test
    public void shoulReturnCarsAfterGetting() {
        addCarsForTest();
        Collection<CarSaleInfo> cars = service.getCars();
        assertThat(cars.contains(new CarSaleInfo(1L, test_car, price, contact)));
        assertThat(cars.contains(new CarSaleInfo(2L, test_car, price, contact)));
    }

    @Test
    public void shoulReturnEmptyAfterGettingNotExistingCar() {
        assertThat(service.getCar(123L)).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnTrueDuringRemoveCarWhenExist() {
        addCarsForTest();
        assertThat(service.removeCar(1L)).isTrue();
        assertThat(service.getCars()).isNotEmpty();
        assertThat(service.removeCar(2L)).isTrue();
        assertThat(service.getCars()).isEmpty();
    }

    @Test
    public void shouldReturnFalseDuringRemoveCarWhenCarDoesNotExist() {
        assertThat(service.removeCar(Long.MAX_VALUE)).isFalse();
    }

    private void addCarsForTest() {
        service.addCar(test_car, price, contact);
        service.addCar(test_car, price, contact);
    }
}