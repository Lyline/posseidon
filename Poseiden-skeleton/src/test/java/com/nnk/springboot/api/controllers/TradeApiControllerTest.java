package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TradeApiController.class)
class TradeApiControllerTest {

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
  void givenTwoTradeSavedWhenGetAllThenTradeListWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(trade,trade1));

    //When
    mockMvc.perform(get("/api/trades")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].tradeId",is(1)))
        .andExpect(jsonPath("$[0].account",is("Account_test")))
        .andExpect(jsonPath("$[0].type",is("Type_test")))
        .andExpect(jsonPath("$[0].buyQuantity",is(10.0)))

        .andExpect(jsonPath("$[1].tradeId",is(2)))
        .andExpect(jsonPath("$[1].account",is("Account_test1")))
        .andExpect(jsonPath("$[1].type",is("Type_test1")))
        .andExpect(jsonPath("$[1].buyQuantity",is(20.0)));
  }

  @Test
  void givenNoTradeSavedWhenGetAllThenTradeListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/trades")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }
}