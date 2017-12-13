package com.playtika.carshop.service;

import com.playtika.carshop.converter.Converter;
import com.playtika.carshop.dao.CarDao;
import com.playtika.carshop.dao.CarShopDao;
import com.playtika.carshop.dao.PersonDao;
import com.playtika.carshop.domain.CarSaleInfo;
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
    private CarDao carDao;
    @Mock
    private CarShopDao carShopDao;
    @Mock
    private PersonDao personDao;
    @Mock
    private Converter converter;

    @Before
    public void init(){
        service = new CarServiceImpl(converter, carDao, carShopDao, personDao);
    }

    @Test
    public void shouldReturnEmptyAfterGettingNotExistingCar() {
        long id = 123L;
        when(carShopDao.findOne(id)).thenReturn(null);

        Optional<CarSaleInfo> expectedCar = service.getCar(id);

        assertThat(expectedCar).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldReturnTrueDuringRemoveCarWhenExist() {
        long id = 1L;
        when(carShopDao.exists(anyLong())).thenReturn(true);

        boolean result = service.removeCar(id);

        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalseDuringRemoveCarWhenCarDoesNotExist() {
        long id = Long.MAX_VALUE;
        when(carShopDao.exists(anyLong())).thenReturn(false);

        boolean result = service.removeCar(anyLong());

        assertThat(result).isFalse();
    }
}