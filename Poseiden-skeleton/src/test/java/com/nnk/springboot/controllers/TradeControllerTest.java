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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

  @Test
  void showTradeNameForm() throws Exception {
    //When
    mockMvc.perform(get("/trade/add"))
        .andExpect(view().name("trade/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account")))
        .andExpect(content().string(containsString("Type")))
        .andExpect(content().string(containsString("Buy quantity")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Add Trade")));
  }

  @Test
  void givenANewTradeValidWhenCreateThenTradeIsSavedAndTradeHomeDisplayed() throws Exception {
    //Given
    when(service.create(any())).thenReturn(trade);
    when(service.getAll()).thenReturn(List.of(trade));

    //When
    mockMvc.perform(post("/trade/validate")
            .param("account","Account_test")
            .param("type","Type_test")
            .param("buyQuantity",String.valueOf(10)))
        .andExpect(view().name("trade/list"))
        .andExpect(status().isCreated())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account_test")))
        .andExpect(content().string(containsString("Type_test")))
        .andExpect(content().string(containsString("10")));
  }

  @Test
  void givenANewTradeNotValidWhenCreateThenTradeFormDisplayedWithErrorMessage() throws Exception {
    //When
    mockMvc.perform(post("/trade/validate"))
        .andExpect(view().name("trade/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account must not be empty")))
        .andExpect(content().string(containsString("Type must not be empty")))
        .andExpect(content().string(containsString("Buy quantity must not be null")));
  }

  @Test
  void showUpdateForm() throws Exception {
    //Given
    when(service.getById(anyInt())).thenReturn(trade);

    //When
    mockMvc.perform(get("/trade/update/1")
            .param("account","Account_test")
            .param("type","Type_test")
            .param("buyQuantity",String.valueOf(10)))
        .andExpect(view().name("trade/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account")))
        .andExpect(content().string(containsString("Account_test")))

        .andExpect(content().string(containsString("Type")))
        .andExpect(content().string(containsString("Type_test")))

        .andExpect(content().string(containsString("Buy quantity")))
        .andExpect(content().string(containsString("10")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Update Trade")));
  }

  @Test
  void givenAExistingTradeWhenValidUpdateThenTradeIsUpdatedAndTradeHomeDisplayed() throws Exception {
    //Given
    when(service.update(anyInt(),any())).thenReturn(trade);
    when(service.getAll()).thenReturn(List.of(trade));

    //When
    mockMvc.perform(post("/trade/update/1")
        .param("account","Account_test")
        .param("type","Type_test")
        .param("buyQuantity",String.valueOf(10)))

        .andExpect(view().name("trade/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account_test")))
        .andExpect(content().string(containsString("Type_test")))
        .andExpect(content().string(containsString("10")));
  }

  @Test
  void givenAExistingTradeWhenNotValidUpdateThenTradeUpdateFormDisplayedWithErrorMessages() throws Exception {
    //When
    mockMvc.perform(post("/trade/update/1"))
        .andExpect(view().name("trade/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account must not be empty")))
        .andExpect(content().string(containsString("Type must not be empty")))
        .andExpect(content().string(containsString("Buy quantity must not be null")));
  }

  @Test
  void givenATradeWhenDeleteThenTradeIsDeletedAndTradeHomeDisplayed() throws Exception {
    //When
    mockMvc.perform(get("/trade/delete/1"))
        .andExpect(view().name("trade/list"))
        .andExpect(status().isOk());
  }
}