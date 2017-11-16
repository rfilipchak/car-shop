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
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CarShopControllerSystemTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void shouldCreateANewCarSuccessfullyForSystemContext() throws Exception {
        //add new car
        String result = this.mockMvc.perform(post("/carSaleInfo")
                .content("{\"type\": \"Ford\",\"year\":2017}")
                .param("price", "200000").param("contact", "contact")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        Long id = Long.parseLong(result);
        assertThat(id).isNotNull().isEqualTo(1L);

        //get all car
        this.mockMvc.perform(get("/carSaleInfo"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].car.type").value("Ford"))
                .andExpect(jsonPath("$[0].car.year").value(2017))
                .andExpect(jsonPath("$[0].price").value(200000))
                .andExpect(jsonPath("$[0].contact").value("contact"));
        //get car by id
        this.mockMvc.perform(get("/carSaleInfo/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.car.type").value("Ford"))
                .andExpect(jsonPath("$.car.year").value(2017))
                .andExpect(jsonPath("$.price").value(200000))
                .andExpect(jsonPath("$.contact").value("contact"));
        //remove car
        this.mockMvc.perform(delete("/carSaleInfo/" + 1L))
                .andExpect(status().isOk());
    }

}

