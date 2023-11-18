package com.greenatom.controller;

import com.greenatom.domain.dto.item.OrderItemRequestDTO;
import com.greenatom.domain.dto.order.OrderRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@ActiveProfiles("test")
public class OrderControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    public void testAddDraftOrder() throws Exception {
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setClientId(1L);
        orderRequestDTO.setEmployeeId(1L);

        OrderItemRequestDTO orderItemRequestDTO = new OrderItemRequestDTO();
        orderItemRequestDTO.setProductId(1L);
        orderItemRequestDTO.setOrderAmount(5L);

        List<OrderItemRequestDTO> orderItemList = Collections.singletonList(orderItemRequestDTO);
        orderRequestDTO.setOrderItemList(orderItemList);

        int maxRetries = 5;
        AtomicInteger retryCount = new AtomicInteger();
        AtomicBoolean success = new AtomicBoolean(false);

        while (retryCount.get() < maxRetries && !success.get()) {
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/api/orders/draft")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(orderRequestDTO)))
                    .andExpect(result -> {
                        if (result.getResponse().getStatus() == 401) {
                            retryCount.getAndIncrement();
                            TimeUnit.SECONDS.sleep(1);
                        } else {
                            success.set(true);
                        }
                    });
        }
        if (!success.get()) {
            throw new AssertionError("Failed to get a successful response after maximum retries");
        }
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
