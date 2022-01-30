package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
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

@WebMvcTest(BidListController.class)
class BidListControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BidListServiceImpl service;

  @Test
  void givenTwoBidListSavedWhenGetHomeThenDisplayedBidListTableWithTwoResults() throws Exception {
    //Given
    BidList bid= new BidList("Account Test", "Type Test", 10d);
    bid.setBidListId(1);
    BidList bid1= new BidList("Account_Test1", "Type_Test1 ", 20d);
    bid1.setBidListId(2);

    when(service.getAll()).thenReturn(List.of(bid,bid1));

    //When
    mockMvc.perform(get("/bidList/list"))
        .andExpect(view().name("bidList/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account Test")))
        .andExpect(content().string(containsString("Type Test")))
        .andExpect(content().string(containsString("10")))

        .andExpect(content().string(containsString("2")))
        .andExpect(content().string(containsString("Account_Test1")))
        .andExpect(content().string(containsString("Type_Test1")))
        .andExpect(content().string(containsString("20")));
  }

  @Test
  void givenNoBidListSavedWhenGetHomeThenDisplayedBidListTableWithErrorMessage() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/bidList/list"))
        .andExpect(view().name("bidList/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Not bid list saved")));
  }

  @Test
  void givenUserWantCreateANewBidListWhenGetAddThenDisplayedAddBidListForm() throws Exception {
    //Given
    //When
    mockMvc.perform(get("/bidList/add"))
        .andExpect(view().name("bidList/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account")))
        .andExpect(content().string(containsString("Type")))
        .andExpect(content().string(containsString("Bid quantity")))
        .andExpect(content().string(containsString("Add BidList")))
        .andExpect(content().string(containsString("Cancel")));
  }

  @Test
  void addBidForm() {
    //Given
    //When
    //Then
  }

  @Test
  void validate() {
    //Given
    //When
    //Then
  }

  @Test
  void showUpdateForm() {
    //Given
    //When
    //Then
  }

  @Test
  void updateBid() {
    //Given
    //When
    //Then
  }

  @Test
  void deleteBid() {
    //Given
    //When
    //Then
  }
}