package by.nti_team.test_work.controller.web.rest;

import by.nti_team.test_work.model.Planet;
import by.nti_team.test_work.storage.api.IPlanetRepository;
import by.nti_team.test_work.view.api.IPlanetView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlanetRestServletMockMvcIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    IPlanetView planetView;

    @Autowired
    IPlanetRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void giveListPlanet_whenGet_thenStatus200andReturnResults() throws Exception {

        List<Planet> planetList = valueProviderPlanets().map(this::saveEntity).collect(Collectors.toList());

        mockMvc.perform(
                get("/planets"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllPlanetsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(planetList)));
    }

    @Test
    public void givePlanet_whenDelete_thenStatus202andReturns_and_thenStatus400andErrorReturns() throws Exception {

        List<Planet> planetList = valueProviderPlanets().map(this::saveEntity).collect(Collectors.toList());

        mockMvc.perform(
                delete("/planets")
                        .content(objectMapper.writeValueAsString(planetList.get(5)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(handler().methodName("destroyPlanet"))
                .andExpect(content().json(objectMapper.writeValueAsString(planetList.get(5))));

        mockMvc.perform(
                delete("/planets")
                        .content(objectMapper.writeValueAsString(planetList.get(5)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("destroyPlanet"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Not Found Entity"))
                .andExpect(jsonPath("$.debugMessage").value("entity is not found"));
    }

    @Test
    public void givenPlanet_whenPost_thenStatus201andLordReturned_and_thenStatus400andErrorReturns() throws Exception {

        List<Planet> planetList = valueProviderPlanets().map(this::saveEntity).collect(Collectors.toList());
        Planet planet = createTestPlanet("Saturn");

        mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(planet))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(handler().methodName("createPlanet"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Saturn"));

        mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(planet))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("createPlanet"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Entity Already Exists"))
                .andExpect(jsonPath("$.debugMessage").value("entity already exists"));
    }

    private Planet saveEntity(Planet planet) {
        return repository.save(planet);
    }

    private Planet createTestPlanet(String name) {
        Planet planet = new Planet();
        planet.setName(name);
        return planet;
    }

    public Stream<Planet> valueProviderPlanets() {
        return Stream.of(
                createTestPlanet("Earth"),
                createTestPlanet("Mars"),
                createTestPlanet("Mercury"),
                createTestPlanet("Venus"),
                createTestPlanet("Jupiter"),
                createTestPlanet("Uranus "),
                createTestPlanet("Pluto "),
                createTestPlanet("Neptune")
        );
    }

}