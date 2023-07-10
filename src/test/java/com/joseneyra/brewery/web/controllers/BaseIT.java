package com.joseneyra.brewery.web.controllers;

import com.joseneyra.brewery.repositories.BeerInventoryRepository;
import com.joseneyra.brewery.repositories.BeerRepository;
import com.joseneyra.brewery.repositories.CustomerRepository;
import com.joseneyra.brewery.services.BeerService;
import com.joseneyra.brewery.services.BreweryService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseIT {

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @MockBean
    BeerRepository beerRepository;

    @MockBean
    BeerInventoryRepository beerInventoryRepository;

    @MockBean
    BreweryService breweryService;

    @MockBean
    CustomerRepository customerRepository;

    @MockBean
    BeerService beerService;

    UUID myUUID;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

        myUUID = UUID.randomUUID();
    }
}
