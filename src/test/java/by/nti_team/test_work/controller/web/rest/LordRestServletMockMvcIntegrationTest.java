package by.nti_team.test_work.controller.web.rest;


import by.nti_team.test_work.model.Lord;
import by.nti_team.test_work.model.Planet;
import by.nti_team.test_work.storage.api.ILordRepository;
import by.nti_team.test_work.view.api.ILordView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LordRestServletMockMvcIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ILordView lordView;

    @Autowired
    ILordRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test public void givenLords_whenGetLords_thenStatus200() throws Exception {

        List<Lord> lordList = valueProviderLords().map(this::saveEntity).collect(Collectors.toList());

        mockMvc.perform(
                get("/lords"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllLordsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lordList)))
        ;
    }

    @Test
    public void giveEmptyListLords_whenGet_thenStatus200andReturnResults() throws Exception {

        List<Lord> lordList = valueProviderLords().map(this::saveEntity).collect(Collectors.toList());

        mockMvc.perform(
                get("/lords/empty"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getEmptyLordsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lordList)))
                .andExpect(jsonPath("$.planets").doesNotExist());
    }

    @Test
    public void giveTop10ListLords_whenGet_thenStatus200andReturnResults() throws Exception {

       Stream<Lord> lordStream = valueProviderLords().map(this::saveEntity);

        List<Lord> lordList = lordStream.limit(10)
                .sorted(Comparator.comparing(Lord::getAge)).collect(Collectors.toList());

        mockMvc.perform(
                get("/lords/top"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getTopLordsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lordList)))
                .andExpect(jsonPath("$.[0].age").value(lordList.get(0).getAge()))
                .andExpect(jsonPath("$.[5].age").value(lordList.get(5).getAge()))
                .andExpect(jsonPath("$.[8].age").value(lordList.get(8).getAge()));
    }

    @Test
    public void givenId_whenGetExistingLord_thenStatus200andPersonReturned_and_thenStatus400andErrorReturns() throws Exception {

        Lord lord = saveEntity(createTestLord("Michail", 57));
        long id = lord.getId();
        long anyId = 1234;

        mockMvc.perform(
                get("/lords/{id}", id))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getLordByIdJson"))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Michail"))
                .andExpect(jsonPath("$.age").value(57));

        mockMvc.perform(
                get("/lords/{id}", anyId))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("getLordByIdJson"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Not Found Entity"))
                .andExpect(jsonPath("$.debugMessage").value("entity with 'id = " + anyId + "' is not found"));
    }

    @Test
    public void givenLord_whenAdd_thenStatus201andLordReturned_and_thenStatus400andErrorReturns() throws Exception {

        Set<Planet> planets = new HashSet<>();
        planets.add(createTestPlanet("Mars"));
        Lord lord = createTestLord("Aragon",1234);
        lord.setPlanets(planets);

        mockMvc.perform(
                post("/lords")
                        .content(objectMapper.writeValueAsString(lord))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(handler().methodName("addLord"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Aragon"))
                .andExpect(jsonPath("$.age").value(1234))
                .andExpect(jsonPath("$.planets").isArray())
                .andExpect(jsonPath("$.planets[0].id").isNumber())
                .andExpect(jsonPath("$.planets[0].name").value("Mars"));

        mockMvc.perform(
                post("/lords")
                        .content(objectMapper.writeValueAsString(lord))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("addLord"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("Entity Already Exists"))
                .andExpect(jsonPath("$.debugMessage").value("entity already exists"));
    }


    @Test
    public void giveLord_whenPut_thenStatus200andReturns_and_thenStatus400andErrorReturns() throws Exception {

        Set<Planet> planets = new HashSet<>();
        planets.add(createTestPlanet("Moon"));
        Stream<Lord> stl = valueProviderLords().map(this::saveEntity);
        Lord lord = stl.findFirst().orElse(new Lord());
        lord.setAge(666);
        lord.setName("NEW");
        lord.setPlanets(planets);
        Lord notExist = new Lord(1334L,"NotExist", 2223, null);

        mockMvc.perform(
                put("/lords")
                        .content(objectMapper.writeValueAsString(lord))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("updateLord"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("NEW"))
                .andExpect(jsonPath("$.age").value(666))
                .andExpect(jsonPath("$.planets").isArray())
                .andExpect(jsonPath("$.planets[0].id").isNumber())
                .andExpect(jsonPath("$.planets[0].name").value("Moon"));

        mockMvc.perform(
                put("/lords")
                        .content(objectMapper.writeValueAsString(notExist))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(handler().methodName("updateLord"))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Not Found Entity"))
                .andExpect(jsonPath("$.debugMessage").value("entity with 'id = " + notExist.getId() + "' is not found"));
    }

    public  Stream<Lord> valueProviderLords() {
        return Stream.of(
                createTestLord("Tor", 1235),
                createTestLord("Tangos", 122),
                createTestLord("Irakli", 233),
                createTestLord("Rank", 34),
                createTestLord("Odin", 201),
                createTestLord("Zeus", 555),
                createTestLord("Va", 788),
                createTestLord("Baal", 201),
                createTestLord("QRt", 34),
                createTestLord("OdiIPn", 12),
                createTestLord("GTh", 56)
        );
    }

    private Lord saveEntity(Lord lord) {
        return repository.save(lord);
    }

    private Lord createTestLord(String name, Integer age) {
        Lord lord = new Lord();
        lord.setName(name);
        lord.setAge(age);
        return lord;
    }

    private Planet createTestPlanet(String name) {
        Planet planet = new Planet();
        planet.setName(name);
        return planet;
    }

}