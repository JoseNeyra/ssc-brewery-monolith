package com.joseneyra.brewery.web.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public abstract class BaseIT {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    protected UUID myUUID;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();

        myUUID = UUID.randomUUID();
    }

    public static Stream<Arguments> getStreamAllUsers() {
        return Stream.of(Arguments.of("admin", "pass"),
                Arguments.of("user", "pass"),
                Arguments.of("scott", "tiger"));
    }

    public static Stream<Arguments> getStreamNotAdmin() {
        return Stream.of(Arguments.of("user", "pass"),
                Arguments.of("scott", "tiger"));
    }

    public static Stream<Arguments> getStreamAdminCustomer() {
        return Stream.of(Arguments.of("admin", "pass"),
                Arguments.of("scott", "tiger"));
    }
}
