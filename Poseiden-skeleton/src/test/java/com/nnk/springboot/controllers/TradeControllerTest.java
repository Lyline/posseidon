package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
class TradeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TradeServiceImpl service;

  private final Trade trade= new Trade();
  private final Trade trade1= new Trade();

  @BeforeEach
  void setUp() {
    trade.setTradeId(1);
    trade.setAccount("Account_test");
    trade.setType("Type_test");
    trade.setBuyQuantity(10);

    trade1.setTradeId(2);
    trade1.setAccount("Account_test1");
    trade1.setType("Type_test1");
    trade1.setBuyQuantity(20);
  }

  @Test
  void givenTwoTradeWhenTradeHomeThenTradeHomeTableWithTwoElementsDisplayed() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(trade,trade1));

    //When
    mockMvc.perform(get("/trade/list"))
        .andExpect(view().name("trade/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account_test")))
        .andExpect(content().string(containsString("Type_test")))
        .andExpect(content().string(containsString("10")))

        .andExpect(content().string(containsString("2")))
        .andExpect(content().string(containsString("Account_test1")))
        .andExpect(content().string(containsString("Type_test1")))
        .andExpect(content().string(containsString("20")));
  }
}