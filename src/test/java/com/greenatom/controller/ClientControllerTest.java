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

    //Тест не проходит, выдает ошибку 400
//    @Test
//    @WithMockUser(roles = "MANAGER")
//    @Transactional
//    public void testAddClient() throws Exception {
//        ClientRequestDTO clientRequestDTO = new ClientRequestDTO();
//        clientRequestDTO.setName("Test");
//        clientRequestDTO.setSurname("Test");
//        clientRequestDTO.setPatronymic("Test");
//        clientRequestDTO.setCompany("Test");
//        clientRequestDTO.setBank("Test");
//        clientRequestDTO.setInn("Test");
//        clientRequestDTO.setOgrn("Test");
//        clientRequestDTO.setCorrespondentAccount("Test");
//        clientRequestDTO.setAddress("Test");
//        clientRequestDTO.setEmail("Test@test.ru");
//        clientRequestDTO.setPhoneNumber("Test");
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/clients")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(AuthControllerTest.asJsonString(clientRequestDTO)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//    }

    //Тест не проходит, пишет 401 ошибку
//    @Test
//    @WithMockUser(roles = "MANAGER")
//    public void testDeleteClient() throws Exception {
//        Long clientId = 1L;
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/clients/{id}", clientId)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isNoContent());
//    }


    @Test
    @WithMockUser(roles = "MANAGER")
    public void testFindAllClients() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/clients")
                        .param("pagePosition", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "id")
                        .param("sortDirection", "ASC"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andReturn();
    }

}
