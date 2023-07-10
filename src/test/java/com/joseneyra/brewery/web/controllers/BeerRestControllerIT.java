package com.joseneyra.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class BeerRestControllerIT extends BaseIT{

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + myUUID)
                .header("Api-Key", "admin").header("Api-Secret", "badPass"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/" + myUUID))
                .andExpect(status().isOk());
    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + myUUID)
                .header("Api-Key", "admin").header("Api-Secret", "pass"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerHttpBasic() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + myUUID)
                        .with(httpBasic("admin", "pass")))
                        .andExpect(status().is2xxSuccessful());
    }

    @Test
    void deleteBeerNoAuth() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + myUUID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void deleteBeerUrl() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + myUUID)
                        .param("Api-Key", "admin").param("Api-Secret", "pass"))
                .andExpect(status().isOk());
    }
    @Test
    void deleteBeerBadCredsUrl() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + myUUID)
                        .param("Api-Key", "admin").param("Api-Secret", "badPass"))
                .andExpect(status().isUnauthorized());
    }

}
