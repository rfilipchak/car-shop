package com.playtika.carshop.web;

import com.playtika.carshop.Exeprions.CarNotFoundException;
import com.playtika.carshop.domain.Car;
import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.domain.CarSaleRequest;
import com.playtika.carshop.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CarShopController.class)
public class CarShopControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarService carService;

    @Test
    public void shouldCreateANewCarSuccessfully() throws Exception {
        long createdCarId = 1L;
        when(carService.addCar(any(CarSaleRequest.class))).thenReturn(createdCarId);

        String result = mockMvc.perform(post("/carSaleInfo")
                .content("{\"type\": \"Ford\",\"year\":2017}")
                .param("price", "200000").param("contact", "contact")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long id = Long.parseLong(result);
        assertThat(id).isNotNull().isEqualTo(1L);
    }
    @Test
    public void shouldFailCreatingANewCarWithMissingParams() throws Exception {
        mockMvc.perform(post("/carSaleInfo")
                .content("{\"type\": \"Ford\",\"year\":2017}")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value(BAD_REQUEST.getReasonPhrase()));
    }

    @Test
    public void shouldReturnAllCarsIfExist() throws Exception {
        Collection<CarSaleInfo> cars = asList(carSaleInfo(1L, "Ford"), carSaleInfo(2L, "Toyota"));
        when(carService.getCars()).thenReturn(cars);

        mockMvc.perform(get("/carSaleInfo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].car.type").value("Ford"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].car.type").value("Toyota"));
    }

    @Test
    public void shouldReturnEmptyListIfThereAreNoCars() throws Exception {
        when(carService.getCars()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/carSaleInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldReturnCarById() throws Exception {
        Long carId = 1L;
        when(carService.getCar(carId)).thenReturn(carSaleInfo(carId, "Test Car"));

        mockMvc.perform(get("/carSaleInfo/" + carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carId))
                .andExpect(jsonPath("$.car.type").value("Test Car"));
    }

    @Test
    public void shouldReturnErrorIfCarDoesNotExist() throws Exception {
        when(carService.getCar(anyLong())).thenThrow(new CarNotFoundException("Car not found"));

        mockMvc.perform(get("/carSaleInfo/" + 12))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(NOT_FOUND.getReasonPhrase()));
    }

    @Test
    public void shouldRemoveCarById() throws Exception {
        doNothing().when(carService).removeCar(anyLong());

        mockMvc.perform(delete("/carSaleInfo/" + 1L))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorOnRemoveNonExistingCar() throws Exception {
        doThrow(new CarNotFoundException("Car not found")).when(carService).removeCar(anyLong());

        mockMvc.perform(delete("/carSaleInfo/" + 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value(NOT_FOUND.getReasonPhrase()));
    }


    private CarSaleInfo carSaleInfo(Long id, String model) {
        Car testCar = new Car(model, 2017);
        return new CarSaleInfo(id, testCar, 200000, "contact");
    }
}
