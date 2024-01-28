package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espam.api.practical.Application;
import ec.edu.espam.api.practical.domain.Client;
import ec.edu.espam.api.practical.domain.dto.ClientDto;
import ec.edu.espam.api.practical.repository.ClientRepository;
import ec.edu.espam.api.practical.util.Mapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    ObjectMapper objectMapper;
    static Client client;
    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;
    @BeforeAll
    void preconditionCreate() {
        client = clientRepository.findByDni("1103654578")
                .orElseGet(() -> {
                    Client newClient = Client.builder()
                            .name("Orley")
                            .gender("M")
                            .age(37)
                            .dni("1103654578")
                            .address("Sto Domingo")
                            .phone("0991425658")
                            .password("1234")
                            .state(true)
                            .build();
                    return clientRepository.save(newClient);
                });
    }
    @Test
    void getById() throws Exception {
        preconditionCreate();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clients/" + client.getId()));
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.dni", is("1103654578")));
    }
    @Test
    void getAll() throws Exception {
        preconditionCreate();

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/clients"));
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.size()", is(1)));
    }

    @Test
    void create() throws Exception {
        ClientDto clientDto = ClientDto.builder()
                .name("Orley")
                .gender("M")
                .age(37)
                .dni("113234565")
                .address("Sto Domingo")
                .phone("0991425678")
                .password("1234")
                .state(true)
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/clients")
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(clientDto))
        ).andExpect(status().isCreated());

        Optional<Client> optional = clientRepository.findByDni("113234565");

        assertTrue(optional.isPresent());

        clientRepository.delete(optional.get());
    }

    @Test
    void delete() throws Exception {
        preconditionCreate();
        assertTrue(clientRepository.findByDni(client.getDni()).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/{id}", client.getId()))
                .andExpect(status().isOk());

        assertFalse(clientRepository.findByDni(client.getDni()).isPresent());
    }

    /*@Test
    void deleteError() throws Exception {
        assertFalse(clientRepository.findById(0l).isPresent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/clients/{id}", 0))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message", is("Client not found")));
    }*/
    @Test
    void update() throws Exception {
        preconditionCreate();
        assertTrue(clientRepository.findByDni(client.getDni()).isPresent());

        ClientDto clientDto = convertEntityToDto(client);
        clientDto.setName("Wilmer");

        mockMvc.perform(MockMvcRequestBuilders.put("/clients/{id}", client.getId())
                .contentType(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(clientDto))
        ).andExpect(status().isOk());

        Optional<Client> optional = clientRepository.findByDni("1103654578");
        assertTrue(optional.isPresent());
        assertEquals("Wilmer", optional.get().getName());
    }

    private ClientDto convertEntityToDto(Client entity) {
        return Mapper.modelMapper().map(entity, ClientDto.class);
    }
}