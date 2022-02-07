package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

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
  void givenNoBidListSavedWhenGetHomeThenDisplayedBidListTableEmpty() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/bidList/list"))
        .andExpect(view().name("bidList/list"))
        .andExpect(status().isOk());
  }

  @Test
  void showBidListForm() throws Exception {
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
  void givenANewBidListValidWhenValidateThenBidListIsSavedAndBidListHomeDisplayed() throws Exception {
    //Given
    BidList bidSaved= new BidList("Account Test", "Type Test", 10d);
    bidSaved.setBidListId(1);

    when(service.create(any())).thenReturn(bidSaved);
    when(service.getAll()).thenReturn(List.of(bidSaved));

    //When
    mockMvc.perform(post("/bidList/validate")
            .param("account","Account Test")
            .param("type","Type Test")
            .param("bidQuantity","10"))
        .andExpect(view().name("bidList/list"))
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
        .andExpect(view().name("bidList/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account is mandatory")))
        .andExpect(content().string(containsString("Type is mandatory")))
        .andExpect(content().string(containsString("Enter only numbers")));
  }

    @Test
  void givenAExistingBidListWhenShowBidListUpdateThenTheFormToUpdateWithBidListDisplayed() throws Exception {
    //Given
    BidList bidRecorded=new BidList();
    bidRecorded.setBidListId(1);
    bidRecorded.setAccount("Account_Test");
    bidRecorded.setType("Type_Test");
    bidRecorded.setBidQuantity(10);

    when(service.getBidListById(anyInt())).thenReturn(Optional.of(bidRecorded));

    //When
    mockMvc.perform(get("/bidList/update/1"))
        .andExpect(view().name("bidList/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account")))
        .andExpect(content().string(containsString("Account_Test")))

        .andExpect(content().string(containsString("Type")))
        .andExpect(content().string(containsString("Type_Test")))

        .andExpect(content().string(containsString("Bid quantity")))
        .andExpect(content().string(containsString("10")))

        .andExpect(content().string(containsString("Update BidList")))
        .andExpect(content().string(containsString("Cancel")));
  }

  @Test
  void givenABidListWhenPostUpdateThenTheBidListIsUpdatedAndListHomeDisplayed() throws Exception {
    //Given
    BidList bidRecorded=new BidList();
    bidRecorded.setBidListId(1);
    bidRecorded.setAccount("Account_Test");
    bidRecorded.setType("Type_Test");
    bidRecorded.setBidQuantity(10);

    BidList bidToUpdate=new BidList();
    bidToUpdate.setBidListId(1);
    bidToUpdate.setAccount("Account_updated");
    bidToUpdate.setType("Type_updated");
    bidToUpdate.setBidQuantity(120);

    when(service.getBidListById(anyInt())).thenReturn(Optional.of(bidRecorded));
    when(service.update(any())).thenReturn(bidToUpdate);
    when(service.getAll()).thenReturn(List.of(bidToUpdate));

    //When
    mockMvc.perform(post("/bidList/update/1")
            .param("account","account_updated")
            .param("type","Type_updated")
            .param("bidQuantity", String.valueOf(120)))
        .andExpect(view().name("/bidList/list"))
        .andExpect(status().isCreated())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("Account_updated")))
        .andExpect(content().string(containsString("Type_updated")))
        .andExpect(content().string(containsString("120")));
  }

  @Test
  void givenABidListNotValidWhenPostUpdateThenTheUpdateFormIsDisplayedWithErrorMessage() throws Exception {
    //Given
    BidList bid= new BidList("Account Test", "Type Test", 10d);
    bid.setBidListId(1);

    when(service.getBidListById(anyInt())).thenReturn(Optional.of(bid));

    //When
    mockMvc.perform(post("/bidList/update/1")
            .param("account","")
            .param("type","")
            .param("bidQuantity", String.valueOf(0)))
        .andExpect(view().name("/bidList/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Account is mandatory")))
        .andExpect(content().string(containsString("Type is mandatory")))
        .andExpect(content().string(containsString("Enter only numbers")));
  }

  @Test
  void givenABidListWhenDeleteBidThenBidListIsDeletedAndBidHomeDisplayed() throws Exception {
    //When
    mockMvc.perform(get("/bidList/delete/1"))
        .andExpect(view().name("/bidList/list"))
        .andExpect(status().isOk());
  }
}