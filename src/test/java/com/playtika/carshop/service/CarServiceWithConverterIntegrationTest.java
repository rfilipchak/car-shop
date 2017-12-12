package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.dao.CarDao;
import com.playtika.carshop.dao.CarShopDao;
import com.playtika.carshop.dao.PersonDao;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceWithConverterIntegrationTest {

    private CarService service;
    @Mock
    private CarDao carDao;
    @Mock
    private CarShopDao carShopDao;
    @Mock
    private PersonDao personDao;

    @Before
    public void init() {
        Converter converter = new Converter();
        service = new CarServiceImpl(converter, carDao, carShopDao, personDao);
    }

    @Test
    public void shouldReturnCarById() {
        long id = 1L;
        CarShopEntity bmw = new CarShopEntity(1L, new CarEntity("AA-0177-BH", "BMW", 2017, "black"),
                2000, new PersonEntity("contact"));
        CarSaleInfo bmwToCompare = new CarSaleInfo(1L, new Car("BMW", 2017, "AA-0177-BH", "black"),
                2000, "contact");
        when(carShopDao.findCarShopEntitiesById(anyLong())).thenReturn(bmw);

        assertThat(service.getCar(id))
                .isEqualTo(Optional.ofNullable(bmwToCompare));
    }

    @Test
    public void shouldReturnCarsAfterGetting() {
        CarShopEntity bmw = new CarShopEntity(1L, new CarEntity("AA-0177-BH", "BMW", 2017, "black"),
                2000, new PersonEntity("contact"));
        CarShopEntity ford = new CarShopEntity(2L,
                new CarEntity("AA-0178-BH", "Ford", 2017, "black"),
                2000, new PersonEntity("contact"));
        CarSaleInfo bmwToCompare = new CarSaleInfo(1L, new Car("BMW", 2017, "AA-0177-BH", "black"),
                2000, "contact");
        CarSaleInfo fordToCompare = new CarSaleInfo(2L, new Car("Ford", 2017, "AA-0177-BH", "black"),
                2000, "contact");
        when(carShopDao.findAll()).thenReturn(Arrays.asList(bmw, ford));

        Collection<CarSaleInfo> cars = service.getCars();
        assertThat(cars.contains(bmwToCompare));
        assertThat(cars.contains(fordToCompare));
    }
}
