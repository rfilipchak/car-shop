package com.playtika.carshop.converter;

import com.playtika.carshop.dao.entity.CarEntity;
import com.playtika.carshop.dao.entity.CarShopEntity;
import com.playtika.carshop.dao.entity.PersonEntity;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    Converter converter = new Converter();

    @Test
    public void shouldConvertDomainToCarEntity(){
        Car bmw = new Car("BMW", 2017, "AA-0177-BH", "black");
        CarEntity bmwToCompare = new CarEntity("AA-0177-BH", "BMW", 2017, "black");

        assertThat(converter.domainToCarEntity(bmw).getRegistration()).isEqualTo(bmwToCompare.getRegistration());
        assertThat(converter.domainToCarEntity(bmw).getBrand()).isEqualTo(bmwToCompare.getBrand());
    }

    @Test
    public void shouldConvertDomainToPersonEntity(){
        String contact = "contact";
        PersonEntity contactEntityToCompare = new PersonEntity("contact");

        assertThat(converter.domainToPersonEntity(contact).getContact())
                .isEqualTo(contact);
    }

    @Test
    public void shouldConvertCarShopEntityToCarSaleInfo(){
        CarSaleInfo bmwToCompare = new CarSaleInfo(1L, new Car("BMW", 2017, "AA-0177-BH", "black"),
                2000, "contact");
        CarShopEntity bmw = new CarShopEntity(1L, new CarEntity("AA-0177-BH", "BMW", 2017, "black"),
                2000, new PersonEntity("contact"));

        assertThat(converter.carShopEntityToCarSaleInfo(bmw)).isEqualTo(bmwToCompare);
    }

    @Test
    public void shouldConvertCarShopEntitiesToCarSaleInfoList(){
        CarShopEntity bmw = new CarShopEntity(1L, new CarEntity("AA-0177-BH", "BMW", 2017, "black"),
                2000, new PersonEntity("contact"));
        CarShopEntity ford = new CarShopEntity(2L,
                new CarEntity("AA-0178-BH", "Ford", 2017, "black"),
                2000, new PersonEntity("contact"));
        CarSaleInfo bmwToCompare = new CarSaleInfo(1L, new Car("BMW", 2017, "AA-0177-BH", "black"),
                2000, "contact");
        CarSaleInfo fordToCompare = new CarSaleInfo(2L, new Car("Ford", 2017, "AA-0178-BH", "black"),
                2000, "contact");
        List<CarShopEntity> carShopEntities = Arrays.asList(bmw,ford);
        List<CarSaleInfo> carSaleInfos = Arrays.asList(bmwToCompare,fordToCompare);

        assertThat(converter.carShopEntitiesToCarSaleInfoList(carShopEntities)).isEqualTo(carSaleInfos);
    }

}