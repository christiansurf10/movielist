package com.outsera.movielist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.nio.file.Files;
import java.nio.file.Paths;


@AutoConfigureMockMvc
@SpringBootTest
class MovielistApplicationTests {

	@Test
	void contextLoads() {
	}


    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRetornarStatus200ParaWinnersInterval() throws Exception {
        mockMvc.perform(get("/movies/winners-interval"))
                .andExpect(status().isOk());
    }

    @Test
    void deveConterCamposMinEMaxNoJson() throws Exception {
        mockMvc.perform(get("/movies/winners-interval"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.max").exists());
    }

    @Test
    void deveConterProducerEIntervalNoPrimeiroMin() throws Exception {
        mockMvc.perform(get("/movies/winners-interval"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min[0].producer").exists())
                .andExpect(jsonPath("$.min[0].interval").exists())
                .andExpect(jsonPath("$.min[0].previousWin").exists())
                .andExpect(jsonPath("$.min[0].followingWin").exists());
    }


    @Test
    void resultadoDeveSerExatamenteIgualAoEsperado() throws Exception {
        String expectedJson = new String(Files.readAllBytes(
                Paths.get("src/test/resources/expected-winners-interval.json")
        ));
        mockMvc.perform(get("/movies/winners-interval"))
                .andExpect(content().json(expectedJson, false));
    }
}
