package com.playtika.carshop.dao;

import com.playtika.carshop.dao.entity.CarEntity;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class CarDaoTest extends AbstractDaoTest<CarDao> {

    //Save Car - to ADD
    @Test
    public void shouldReturnNullWhenCarDoesNotExist() {
        Optional<CarEntity> notExistingCar = dao.getCarByRegistration("unknown");
//        boolean carExisting = dao.getCarByRegistration("unknown").isPresent();
//        assertThat(carExisting,is(false));
        assertThat(notExistingCar,is(Optional.empty()));
    }

    @Test
    public void shouldReturnIdAfterAddingCarToCars() {
        long id = addCarToCarsDb("AA-0177-BH");

        assertThat(id, is(notNullValue()));
    }

    @Test
    public void shouldFindCarByRegistration() {
        String registration = "AA-0177-BH";
        long id = addCarToCarsDb(registration);
        CarEntity expectedCar = new CarEntity("BMW", 1980, "AA-0177-BH", "black");
        expectedCar.setId(id);

        CarEntity car = dao.getCarByRegistration(registration).get();

        assertThat(car, samePropertyValuesAs(expectedCar));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void shouldNotAddTheSameCarToCars() {
        addCarToCarsDb("AA-0177-BH");
        addCarToCarsDb("AA-0177-BH");
    }

    private long addCarToCarsDb(String registration) {
        CarEntity car = new CarEntity("BMW", 1980, registration, "black");
        return dao.save(car).getId();
    }
}