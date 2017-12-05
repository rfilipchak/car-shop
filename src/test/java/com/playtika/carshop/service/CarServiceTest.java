package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.repository.CarRepository;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = "com.playtika.carshop.dao")
public class CarServiceTest {
    @Autowired
    private EntityManager em;
    @Autowired
    private CarRepository carRepository;
    private CarService service;

    Car bmw = new Car("BMW", 2017, "AA-0177-BH", "black");
    Car ford = new Car("Ford", 2017, "AA-0178-BH", "black");
    int price = 2000;
    String contact = "contact";

    @Before
    public void init() {
        Converter converter = new Converter();
        service = new CarServiceImpl(carRepository, converter);
    }

    @Test
    public void shouldReturnValidIdAfterAddingCar() {
        long id = addCarsForTest(bmw);
        assertThat(id).isNotNull();
    }

    @Test
    public void shouldCreateNotEqualIdsAfterCreating() {
        long first = addCarsForTest(bmw);
        long second = addCarsForTest(ford);
        assertThat(first)
                .isNotEqualTo(second);
    }

    @Test
    public void shouldReturnCarById() {
        long id = addCarsForTest(bmw);
        CarSaleInfo bmwToCompare = new CarSaleInfo(id, bmw, price, contact);
        assertThat(service.getCar(id))
                .isEqualTo(Optional.ofNullable(bmwToCompare));
    }

    @Test
    public void shouldReturnCarsAfterGetting() {
        long first = addCarsForTest(bmw);
        long second = addCarsForTest(ford);
        Collection<CarSaleInfo> cars = service.getCars();
        assertThat(cars.contains(new CarSaleInfo(first, bmw, price, contact)));
        assertThat(cars.contains(new CarSaleInfo(second, ford, price, contact)));
    }

    @Test
    public void shouldReturnEmptyAfterGettingNotExistingCar() {
        assertThat(service.getCar(123L)).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnTrueDuringRemoveCarWhenExist() {
        long id = addCarsForTest(bmw);
        assertThat(service.removeCar(id)).isTrue();
    }

    @Test
    public void shouldReturnFalseDuringRemoveCarWhenCarDoesNotExist() {
        assertThat(service.removeCar(Long.MAX_VALUE)).isFalse();
    }

    private long addCarsForTest(Car car) {
        long id = service.addCar(car, price, contact);
        return id;
    }
}