package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.CarDao;
import com.playtika.carshop.dao.CarShopDao;
import com.playtika.carshop.dao.PersonDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {

    private CarService service;
    @Mock
    private CarDao carRepo;
    @Mock
    private CarShopDao carShopDao;
    @Mock
    private PersonDao personDao;
    @Mock
    private Converter converter;

    @Before
    public void init(){
        service = new CarServiceImpl(converter,carRepo, carShopDao, personDao);
    }

    @Test
    public void shouldReturnEmptyAfterGettingNotExistingCar() {
        long id = 123L;
        when(carShopDao.findCarShopEntitiesById(id)).thenReturn(null);

        assertThat(service.getCar(id)).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnTrueDuringRemoveCarWhenExist() {
        long id = 1L;
        when(carShopDao.exists(anyLong())).thenReturn(true);

        assertThat(service.removeCar(id)).isTrue();
    }

    @Test
    public void shouldReturnFalseDuringRemoveCarWhenCarDoesNotExist() {
        long id = Long.MAX_VALUE;
        when(carShopDao.exists(anyLong())).thenReturn(false);

        assertThat(service.removeCar(id)).isFalse();
    }
}