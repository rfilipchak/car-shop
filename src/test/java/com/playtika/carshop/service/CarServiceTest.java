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

    @Before
    public void init() {
        CarRepJpa carRepJpa = new CarRepJpaImpl(em);
        service = new CarServiceImpl(carRepJpa);
    }

    Car test_car = new Car("Test Car", 2017, "AA-0177-BH", "black");
    Car test_car2 = new Car("Test Car2", 2017, "AA-0178-BH", "black");
    long price = 2000;
    String contact = "contact";

    @Test
    public void shouldReturnValidIdAfterAddingCar() {

        long id = addCarsForTest(test_car);
        assertThat(id).isNotNull();
    }

    @Test
    public void shouldCreateNotEqualIdsAfterCreating() {
        assertThat(addCarsForTest(test_car))
                .isNotEqualTo(addCarsForTest(test_car2));
    }

    @Test
    public void shouldReturnCarById() {
        long testId1 = addCarsForTest(test_car);
        long testId2 = addCarsForTest(test_car2);

        assertThat(service.getCar(testId1))
                .isEqualTo(Optional.of(new CarSaleInfo(testId1, test_car, price, contact)));
        assertThat(service.getCar(testId2))
                .isEqualTo(Optional.of(new CarSaleInfo(testId2, test_car2, price, contact)));
    }

    @Test
    public void shouldReturnCarsAfterGetting() {
        long testId1 = addCarsForTest(test_car);
        long testId2 = addCarsForTest(test_car2);
        Collection<CarSaleInfo> cars = service.getCars();
        assertThat(cars.contains(new CarSaleInfo(testId1, test_car, price, contact)));
        assertThat(cars.contains(new CarSaleInfo(testId2, test_car2, price, contact)));
    }
    @Test
    public void shouldNotDeleteCarsAfterDeleteCarSaleInfo() {
        long testId1 = addCarsForTest(test_car);
        service.removeCar(testId1);
        assertThat(service.getAllCars()).isNotEmpty();
    }

    @Test
    public void shouldReturnEmptyAfterGettingNotExistingCar() {
        assertThat(service.getCar(123L)).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnTrueDuringRemoveCarWhenExist() {
        long testId1 = addCarsForTest(test_car);
        long testId2 = addCarsForTest(test_car2);
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