package by.nti_team.test_work.controller.web.rest;


import by.nti_team.test_work.model.Lord;
import by.nti_team.test_work.view.api.ILordView;
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


@WebMvcTest(LordRestServlet.class)
class LordRestServletMockMvcUnitTest {

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ILordView lordView;

    @BeforeEach
    public void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private final Lord lord1 = new Lord(1L, "Jane", 12, null);
    private final Lord lord2 = new Lord(2L, "Joe", 46, null);
    private final List<Lord> lordList = Arrays.asList(lord1, lord2);

    @Test
    public void giveListLords_whenGet_thenStatus200andReturnResults() throws Exception {

        Mockito.when(this.lordView.getAll()).thenReturn(lordList);

        mockMvc.perform(
                get("/lords"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getAllLordsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lordList)));
    }


    @Test
    public void giveEmptyListLords_whenGet_thenStatus200andReturnResults() throws Exception {

        Mockito.when(this.lordView.getEmptyLords()).thenReturn(lordList);

        mockMvc.perform(
                get("/lords/empty"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getEmptyLordsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lordList)));
    }

    @Test
    public void giveTopListLords_whenGet_thenStatus200andReturnResults() throws Exception {

        Mockito.when(this.lordView.getTop()).thenReturn(lordList);

        mockMvc.perform(
                get("/lords/top"))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getTopLordsJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lordList)));
    }

    @Test
    public void giveLordById_whenGet_thenStatus200andReturns() throws Exception {

        Mockito.when(this.lordView.getById((Mockito.anyLong()))).thenReturn(lord2);

        mockMvc.perform(
                get("/lords/1")
                        .content(objectMapper.writeValueAsString(lord2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("getLordByIdJson"))
                .andExpect(content().json(objectMapper.writeValueAsString(lord2)));
    }

    @Test
    public void givenLord_whenPost_thenStatus201andLordReturned() throws Exception {

        Mockito.when(this.lordView.addLord(Mockito.any())).thenReturn(lord1);

        mockMvc.perform(
                post("/lords")
                        .content(objectMapper.writeValueAsString(lord1))
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(handler().methodName("addLord"))
                .andExpect(content().json(objectMapper.writeValueAsString(lord1)));
    }

    @Test
    public void giveLord_whenPut_thenStatus200andReturns() throws Exception {

        Mockito.when(this.lordView.updateLord(Mockito.any())).thenReturn(lord2);

        mockMvc.perform(
                put("/lords")
                        .content(objectMapper.writeValueAsString(lord2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(handler().methodName("updateLord"))
                .andExpect(content().json(objectMapper.writeValueAsString(lord2)));
    }

}