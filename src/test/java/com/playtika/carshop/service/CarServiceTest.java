package com.playtika.carshop.service;

import com.playtika.carshop.dao.repository.CarRepJpa;
import com.playtika.carshop.dao.repository.CarRepJpaImpl;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarServiceTest {
    @Autowired
    private EntityManager em;

    private CarService service;

    Car testCar = new Car("Test Car", 2017, "AA-0177-BH", "black");
    Car testCar2 = new Car("Test Car2", 2017, "AA-0178-BH", "black");
    long price = 2000;
    String contact = "contact";

    @Before
    public void init() {
        CarRepJpa carRepJpa = new CarRepJpaImpl(em);
        service = new CarServiceImpl(carRepJpa);
    }

    @Test
    public void shouldReturnValidIdAfterAddingCar() {

        long id = addCarsForTest(testCar);
        assertThat(id).isNotNull();
    }

    @Test
    public void shouldCreateNotEqualIdsAfterCreating() {
        long testId1 = addCarsForTest(testCar);
        long testId2 = addCarsForTest(testCar2);
        assertThat(testId1)
                .isNotEqualTo(testId2);
    }

    @Test
    public void shouldReturnCarById() {
        long testId1 = addCarsForTest(testCar);
        long testId2 = addCarsForTest(testCar2);
        CarSaleInfo carInfo1 = new CarSaleInfo(testId1, testCar, price, contact);
        CarSaleInfo carInfo2 = new CarSaleInfo(testId2, testCar2, price, contact);

        assertThat(service.getCar(testId1))
                .isEqualTo(Optional.ofNullable(carInfo1));
        assertThat(service.getCar(testId2))
                .isEqualTo(Optional.ofNullable(carInfo2));
    }

    @Test
    public void shouldReturnCarsAfterGetting() {
        long testId1 = addCarsForTest(testCar);
        long testId2 = addCarsForTest(testCar2);
        Collection<CarSaleInfo> cars = service.getCars();
        assertThat(cars.contains(new CarSaleInfo(testId1, testCar, price, contact)));
        assertThat(cars.contains(new CarSaleInfo(testId2, testCar2, price, contact)));
    }
    @Test
    public void shouldNotDeleteCarsAfterDeleteCarSaleInfo() {
        long testId1 = addCarsForTest(testCar);
        service.removeCar(testId1);
        assertThat(service.getAllCars()).isNotEmpty();
    }

    @Test
    public void shouldReturnEmptyAfterGettingNotExistingCar() {
        assertThat(service.getCar(123L)).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnTrueDuringRemoveCarWhenExist() {
        long testId1 = addCarsForTest(testCar);
        long testId2 = addCarsForTest(testCar2);
        assertThat(service.removeCar(testId1)).isTrue();
        assertThat(service.removeCar(testId2)).isTrue();
    }

    @Test
    public void shouldReturnFalseDuringRemoveCarWhenCarDoesNotExist() {
        assertThat(service.removeCar(Long.MAX_VALUE)).isFalse();
    }

    private long addCarsForTest(Car car) {
        long testCarId = service.addCar(car, price, contact);
        return testCarId;
    }
}