package com.playtika.carshop.dao;

import com.google.common.collect.ImmutableMap;
import com.playtika.carshop.dao.entity.CarEntity;
import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class CarDaoTest extends AbstractDaoTest<CarDao> {

    @Test
    public void shouldReturnNullWhenCarDoesNotExist() {
        assertThat(dao.getCarEntitiesByRegistration("unknown"), nullValue());
    }

    @Test
    public void shouldReturnIdAfterAddingCarToCars() {
        long id = addCarToCarsDb("AA-0177-BH", "BMW", 1980, "black");

        assertThat(id, is(notNullValue()));
    }

    @Test
    public void shouldFindCarByRegistration(){
        String registration = "AA-0177-BH";
        long id = addCarToCarsDb(registration, "BMW", 1980, "black");
        CarEntity carToCompare = new CarEntity("AA-0177-BH", "BMW", 1980, "black");
        carToCompare.setId(id);

        assertThat(dao.getCarEntitiesByRegistration(registration), samePropertyValuesAs(carToCompare));
    }

    @Test(expected = DuplicateKeyException.class)
    public void shouldNotAddTheSameCar(){
        addCarToCarsDb("AA-0177-BH", "BMW", 1980, "black");
        addCarToCarsDb("AA-0177-BH", "BMW", 1980, "black");
    }

    private long addCarToCarsDb(String registration, String brand, int year, String color) {
        return addRecordToDb("cars", ImmutableMap.of("registration", registration,
                "brand", brand, "car_year", year, "color", color));
    }
}