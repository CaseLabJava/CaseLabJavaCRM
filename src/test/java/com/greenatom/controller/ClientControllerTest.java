package com.greenatom.controller;

import com.greenatom.domain.dto.client.ClientRequestDTO;
import com.greenatom.domain.dto.client.ClientResponseDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@ActiveProfiles("test")
public class ClientControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @WithMockUser(roles = "MANAGER")
    @Transactional
    public void testAddClient() throws Exception {
        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
        clientRequestDTO.setName("Test");
        clientRequestDTO.setAddress("Test");
        clientRequestDTO.setBank("Test");
        clientRequestDTO.setEmail("Test");
        clientRequestDTO.setPatronymic("Test");
        clientRequestDTO.setSurname("Test");


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AuthControllerTest.asJsonString(clientRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("Test"));
    }
}
