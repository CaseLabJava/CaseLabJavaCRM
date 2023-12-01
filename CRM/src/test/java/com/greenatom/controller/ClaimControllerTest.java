package com.greenatom.controller;

import com.greenatom.BaseControllerTest;
import com.greenatom.domain.dto.claim.ClaimCreationDTO;
import com.greenatom.domain.dto.claim.ClaimRequestDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

@SpringBootTest
@ActiveProfiles("test")
public class ClaimControllerTest extends BaseControllerTest {

    @Test
    @WithMockUser(roles = "MANAGER")
    @Transactional
    public void testAddClaim() throws Exception {
        ClaimCreationDTO claimCreationDTO = new ClaimCreationDTO();
        claimCreationDTO.setOrderId(1L);
        Long existingClaimId = 1L;
        claimCreationDTO.setDescription("Товар разбит");

        ClaimRequestDTO claimRequestDTO = new ClaimRequestDTO();
        claimRequestDTO.setDescription("Товар испорчен");

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/claims")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AuthControllerTest.asJsonString(claimCreationDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.orderId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.claimStatus").value("CREATED"));

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/claims/{id}", existingClaimId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(AuthControllerTest.asJsonString(claimRequestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.claimStatus").value("CREATED"));
    }
}
