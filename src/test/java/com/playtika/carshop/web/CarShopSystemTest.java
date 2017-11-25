package com.playtika.carshop.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CarShopSystemTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateANewCarSuccessfullyForSystemContext() throws Exception {
        assertThat(Long.parseLong(addNewCar("Roman"))).isBetween(0L, 10L);
    }

    @Test
    public void shouldReturnAllCarsSuccessfullyForSystemContext() throws Exception {
        addNewCar("Yura");
        this.mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[*].contact").value("Yura"));
    }

    @Test
    public void shouldReturnCarByIdSuccessfullyForSystemContext() throws Exception {

        this.mockMvc.perform(get("/cars/" + addNewCar("Sveta")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.car.type").value("Ford"))
                .andExpect(jsonPath("$.car.year").value(2017))
                .andExpect(jsonPath("$.price").value(200000))
                .andExpect(jsonPath("$.contact").value("Sveta"));
    }

    @Test
    public void shouldRemoveCarByIdSuccessfullyForSystemContext() throws Exception {

        this.mockMvc.perform(delete("/cars/" + addNewCar("Oleg")))
                .andExpect(status().isOk());
    }

    private String addNewCar(String contact) throws Exception {
        return this.mockMvc.perform(post("/cars")
                .content("{\"type\": \"Ford\",\"year\":2017}")
                .param("price", "200000").param("contact", contact)
                .contentType("application/json;charset=UTF-8")).andReturn().getResponse().getContentAsString();
    }
}

