package com.playtika.carshop.web;

import com.playtika.carshop.domain.CarSaleInfo;
import com.playtika.carshop.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CarShopControllerTest {

    @Mock
    private CarService service;
    private CarShopController controller;
    private MockMvc mockMvc;

    @Before
    public void init() {
        controller = new CarShopController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldFailCreatingCarWithMissingBody() throws Exception {
        mockMvc.perform(post("/cars")
                .param("price", "200000").param("contact", "contact")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailCreatingCarWithMissingParams() throws Exception {
        mockMvc.perform(post("/cars")
                .content("{\"type\": \"Ford\",\"year\":2017}")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfCarIdIsNull() throws Exception {
        mockMvc.perform(get("/cars/" + null))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfCarIdDoesNotValid() throws Exception {
        mockMvc.perform(get("/cars/" + "nulfdsfl"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnEmptyListIfWhereNoAddedCars() throws Exception {
        when(service.getCars()).thenReturn(Collections.emptyList());

        Collection<CarSaleInfo> allCars = controller.getAllCars();

        assertThat(allCars).isEmpty();
    }

    @Test
    public void shouldReturnBadRequestIfCarIdDoesNotValidForDelete() throws Exception {
        mockMvc.perform(delete("/cars/" + "njndjsad"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForDeleteWithNullCarId() throws Exception {
        mockMvc.perform(delete("/cars/" + null))
                .andExpect(status().isBadRequest());
    }
}