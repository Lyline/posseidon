package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TradeApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TradeService service;

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

  @Test
  void givenANewValidTradeWhenCreateThenTradeIsSavedAndStatus201() throws Exception {
    //Given
    when(service.create(any())).thenReturn(trade);

    //When
    mockMvc.perform(post("/api/trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"Account_test\","+
                "\"type\":\"Type_test\","+
                "\"buyQuantity\":10.0"+
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.tradeId",is(1)))
        .andExpect(jsonPath("$.account",is("Account_test")))
        .andExpect(jsonPath("$.type",is("Type_test")))
        .andExpect(jsonPath("$.buyQuantity",is(10.0)));
  }

  @Test
  void givenANewNotValidTradeWhenCreateThenTradeIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/api/trades")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"\","+
                "\"type\":\"\","+
                "\"buyQuantity\":0,"+
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenAExistingTradeWhenUpdateThenTradeIsSavedAndStatus201() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(trade));
    when(service.update(anyInt(),any())).thenReturn(trade);

    //When
    mockMvc.perform(put("/api/trades/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"Account_test\","+
                "\"type\":\"Type_test\","+
                "\"buyQuantity\":10.0"+
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.tradeId",is(1)))
        .andExpect(jsonPath("$.account",is("Account_test")))
        .andExpect(jsonPath("$.type",is("Type_test")))
        .andExpect(jsonPath("$.buyQuantity",is(10.0)));
  }

  @Test
  void givenANotValidTradeWhenUpdateThenTradeIsNotSavedAndStatus400() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(trade));
    //When
    mockMvc.perform(put("/api/trades/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"\","+
                "\"type\":\"\","+
                "\"buyQuantity\":0,"+
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenANotExistTradeWhenUpdateThenTradeIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());

    //When
    mockMvc.perform(put("/api/trades/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"Account_test\","+
                "\"type\":\"Type_test\","+
                "\"buyQuantity\":10.0"+
                "}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenAExistTradeWhenDeleteThenTradeIsDeletedAndStatus200() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(trade));
    //When
    mockMvc.perform(delete("/api/trades/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void givenANotExistTradeWhenDeleteThenTradeIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());
    //When
    mockMvc.perform(delete("/api/trades/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}