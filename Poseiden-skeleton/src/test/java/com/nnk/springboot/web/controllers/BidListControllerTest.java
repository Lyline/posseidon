package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
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

@WebMvcTest(BidListController.class)
class BidListControllerTest {

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
  void givenTwoBidListSavedWhenGetHomeThenDisplayedBidListTableWithTwoResults() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(bid,bid1));

    //When
    mockMvc.perform(get("/bidList/list"))
        .andExpect(view().name("/bidList/list"))
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
  void givenNoBidListSavedWhenGetHomeThenDisplayedBidListTableEmpty() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/bidList/list"))
        .andExpect(view().name("/bidList/list"))
        .andExpect(status().isOk());
  }

  @Test
  void showBidListForm() throws Exception {
    //Given
    //When
    mockMvc.perform(get("/bidList/add"))
        .andExpect(view().name("/bidList/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account")))
        .andExpect(content().string(containsString("Type")))
        .andExpect(content().string(containsString("Bid quantity")))
        .andExpect(content().string(containsString("Add BidList")))
        .andExpect(content().string(containsString("Cancel")));
  }

  @Test
  void givenANewBidListValidWhenValidateThenBidListIsSavedAndBidListHomeDisplayed() throws Exception {
    //Given
    when(service.create(any())).thenReturn(bid);
    when(service.getAll()).thenReturn(List.of(bid));

    //When
    mockMvc.perform(post("/bidList/validate")
            .param("account","Account Test")
            .param("type","Type Test")
            .param("bidQuantity","10"))
        .andExpect(view().name("/bidList/list"))
        .andExpect(status().isCreated())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account Test")))
        .andExpect(content().string(containsString("Type Test")))
        .andExpect(content().string(containsString("10")));
    //Then
  }

  @Test
  void givenANewBidListNotValidWhenValidateThenBidListIsNotSavedAndDisplayedErrorMessage() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/bidList/validate")
            .param("account","")
            .param("type","")
            .param("bidQuantity", String.valueOf(0)))
        .andExpect(view().name("/bidList/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account is mandatory")))
        .andExpect(content().string(containsString("Type is mandatory")))
        .andExpect(content().string(containsString("Enter only numbers")));
  }

    @Test
  void showUpdateForm() throws Exception {
    //Given
    when(service.getById(anyInt())).thenReturn(bid);

    //When
    mockMvc.perform(get("/bidList/update/1")
            .param("account","Account Test")
            .param("type","Type Test")
            .param("bidQuantity","10"))

        .andExpect(view().name("/bidList/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account")))
        .andExpect(content().string(containsString("Type")))
        .andExpect(content().string(containsString("Bid quantity")))

        .andExpect(content().string(containsString("Update BidList")))
        .andExpect(content().string(containsString("Cancel")));
  }

  @Test
  void givenABidListWhenPostUpdateThenTheBidListIsUpdatedAndListHomeDisplayed() throws Exception {
    //Given
    when(service.update(anyInt(),any())).thenReturn(bid);
    when(service.getAll()).thenReturn(List.of(bid));

    //When
    mockMvc.perform(post("/bidList/update/1")
            .param("account","Account Test")
            .param("type","Type Test")
            .param("bidQuantity","10"))
        .andExpect(view().name("/bidList/list"))
        .andExpect(status().isCreated())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account Test")))
        .andExpect(content().string(containsString("Type Test")))
        .andExpect(content().string(containsString("10")));
  }

  @Test
  void givenABidListNotValidWhenPostUpdateThenTheUpdateFormIsDisplayedWithErrorMessage() throws Exception {
    //Given
   when(service.getById(anyInt())).thenReturn(bid);

    //When
    mockMvc.perform(post("/bidList/update/1"))
        .andExpect(view().name("redirect:1"))
        .andExpect(status().is3xxRedirection());
  }

  @Test
  void givenABidListWhenDeleteBidThenBidListIsDeletedAndBidHomeDisplayed() throws Exception {
    //When
    mockMvc.perform(get("/bidList/delete/1"))
        .andExpect(view().name("/bidList/list"))
        .andExpect(status().isOk());
  }
}