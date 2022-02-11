package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
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

@WebMvcTest(BidListApiController.class)
class BidListApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BidListServiceImpl service;

  private final BidList bid= new BidList();
  private final BidList bid1= new BidList();

  @BeforeEach
  void setUp() {
    bid.setBidListId(1);
    bid.setAccount("Account Test");
    bid.setType("Type Test");
    bid.setBidQuantity(10);

    bid1.setBidListId(2);
    bid1.setAccount("Account_Test1");
    bid1.setType("Type_Test1");
    bid1.setBidQuantity(20);
  }

  @Test
  void givenTwoBidListSavedWhenGetAllThenBidListWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(bid,bid1));

    //When
    mockMvc.perform(get("/api/bidList")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].bidListId",is(1)))
        .andExpect(jsonPath("$[0].account",is("Account Test")))
        .andExpect(jsonPath("$[0].type",is("Type Test")))
        .andExpect(jsonPath("$[0].bidQuantity",is(10.0)))

        .andExpect(jsonPath("$[1].bidListId",is(2)))
        .andExpect(jsonPath("$[1].account",is("Account_Test1")))
        .andExpect(jsonPath("$[1].type",is("Type_Test1")))
        .andExpect(jsonPath("$[1].bidQuantity",is(20.0)));
  }

  @Test
  void givenNoBidListSavedWhenGetAllThenBidListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/bidList")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }
}