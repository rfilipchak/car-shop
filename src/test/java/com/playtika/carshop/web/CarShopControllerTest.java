package com.playtika.carshop.web;

import com.playtika.carshop.service.CarService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CarShopControllerTest {
    @Mock
    private CarService carService;

    private CarShopController carShopController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        carShopController = new CarShopController(carService);
        mockMvc = MockMvcBuilders.standaloneSetup(carShopController).build();
    }

    @Test
    public void shouldFailCreatingCarWithMissingBody() throws Exception {
        mockMvc.perform(post("/carSaleInfo")
                .param("price", "200000").param("contact", "contact")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFailCreatingCarWithMissingParams() throws Exception {
        mockMvc.perform(post("/carSaleInfo")
                .content("{\"type\": \"Ford\",\"year\":2017}")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfCarIdIsNull() throws Exception {
        mockMvc.perform(get("/carSaleInfo/" + null))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestIfCarIdDoesNotValid() throws Exception {
        mockMvc.perform(get("/carSaleInfo/" + "nulfdsfl"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOkForCorrecrFormatRequestedCarId() throws Exception {
        mockMvc.perform(get("/carSaleInfo/" + 123L))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnEmptyListIfWhereNoAddedCars() throws Exception {
        when(carService.getCars()).thenReturn(Collections.emptyList());
        assertThat(carShopController.getAllCars()).isEmpty();
    }

    @Test
    public void shouldReturnOkForCorrecrFormatRequestedCarIdForDelete() throws Exception {
        mockMvc.perform(delete("/carSaleInfo/" + 123L))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnBadRequestIfCarIdDoesNotValidForDelete() throws Exception {
        mockMvc.perform(delete("/carSaleInfo/" + "njndjsad"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnBadRequestForDeleteWithNullCarId() throws Exception {
        mockMvc.perform(delete("/carSaleInfo/" + null))
                .andExpect(status().isBadRequest());
    }
}