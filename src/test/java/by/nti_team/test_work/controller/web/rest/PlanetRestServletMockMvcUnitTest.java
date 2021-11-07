package by.nti_team.test_work.controller.web.rest;

import by.nti_team.test_work.model.Planet;
import by.nti_team.test_work.view.api.IPlanetView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlanetRestServlet.class)
class PlanetRestServletMockMvcUnitTest {

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private IPlanetView planetView;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final Planet planet1 = new Planet(1L,"Mars",null);
    private final Planet planet2 = new Planet(2L, "Earth", null);
    private final List<Planet> planetList = Arrays.asList(planet1, planet2);


    @Test
    public void giveListPlanet_whenGet_thenStatus200andReturnResults() throws Exception {

        Mockito.when(this.planetView.getAll()).thenReturn(planetList);

        mockMvc.perform(
                get("/planets"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllPlanetsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(planetList)));
    }

    @Test
    public void givePlanet_whenDelete_thenStatus202andReturns() throws Exception {

        Mockito.when(this.planetView.getById((Mockito.anyLong()))).thenReturn(planet2);

        mockMvc.perform(
                delete("/planets")
                        .content(objectMapper.writeValueAsString(planet2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(handler().methodName("destroyPlanet"))
                .andExpect(content().json(objectMapper.writeValueAsString(planet2)));
    }

    @Test
    public void givenPlanet_whenPost_thenStatus201andLordReturned() throws Exception {

        Mockito.when(this.planetView.addPlanet(Mockito.any())).thenReturn(planet1);

        mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(planet1))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(handler().methodName("createPlanet"))
                .andExpect(content().json(objectMapper.writeValueAsString(planet1)));
    }

}