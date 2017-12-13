package com.playtika.carshop.converter;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    private Converter converter = new Converter();

    @Test
    public void shouldConvertDomainToCarEntity() {
        Car bmw = new Car("BMW", 2017, "AA-0177-BH", "black");
        CarEntity bmwToCompare = new CarEntity("BMW", 2017, "AA-0177-BH", "black");

        CarEntity bmwEntity = converter.domainToCarEntity(bmw);

        assertThat(bmwEntity).isEqualTo(bmwToCompare);
    }

    @Test
    public void shouldConvertDomainToPersonEntity() {
        String contact = "contact";
        PersonEntity personToCompare = new PersonEntity(contact);
        
        PersonEntity person = converter.domainToPersonEntity(contact);

        assertThat(person).isEqualTo(personToCompare);
    }

    @Test
    public void shouldConvertCarShopEntityToCarSaleInfo() {
        CarSaleInfo expectedCar = generateCarSaleInfo(1L,"AA-0177-BH");
        CarShopEntity car = generateCarShopEntity(1L,"AA-0177-BH");

        CarSaleInfo carDomain = converter.carShopEntityToCarSaleInfo(car);
        
        assertThat(carDomain).isEqualTo(expectedCar);
    }

    @Test
    public void shouldConvertCarShopEntitiesToCarSaleInfoList() {
        CarShopEntity first = generateCarShopEntity(1L,"AA-0177-BH");
        CarShopEntity second = generateCarShopEntity(2L,"AA-0178-BH");

        CarSaleInfo expectedFirst = generateCarSaleInfo(1L,"AA-0177-BH");
        CarSaleInfo expectedSecond = generateCarSaleInfo(2L,"AA-0178-BH");
        List<CarShopEntity> carShopEntities = Arrays.asList(first, second);
        List<CarSaleInfo> carSaleInfos = Arrays.asList(expectedFirst, expectedSecond);

        Collection<CarSaleInfo> carShopEntityToDomain = converter.carShopEntitiesToCarSaleInfoList(carShopEntities);

        assertThat(carShopEntityToDomain).isEqualTo(carSaleInfos);
    }

    private CarShopEntity generateCarShopEntity(long id, String registration){
        return new CarShopEntity(id, new CarEntity("BMW", 2017, registration, "black"),
                2000, new PersonEntity("contact"));
    }
    private CarSaleInfo generateCarSaleInfo(long id, String registration){
        return new CarSaleInfo(id, new Car("BMW", 2017, registration, "black"),
                2000, "contact");
    }
}