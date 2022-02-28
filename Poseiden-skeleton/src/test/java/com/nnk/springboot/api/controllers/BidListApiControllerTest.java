package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
class BidListApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BidListService service;

  private final BidList bid= new BidList();
  private final BidList bid1= new BidList();

  @BeforeEach
  void setUp() {


    bid.setId(1);
    bid.setAccount("Account Test");
    bid.setType("Type Test");
    bid.setBidQuantity(10);

    bid1.setId(2);
    bid1.setAccount("Account_Test1");
    bid1.setType("Type_Test1");
    bid1.setBidQuantity(20);
  }

  @Test
  void givenTwoBidListSavedWhenGetAllThenBidListWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(bid,bid1));

    //When
    mockMvc.perform(get("/api/bids")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].id",is(1)))
        .andExpect(jsonPath("$[0].account",is("Account Test")))
        .andExpect(jsonPath("$[0].type",is("Type Test")))
        .andExpect(jsonPath("$[0].bidQuantity",is(10.0)))

        .andExpect(jsonPath("$[1].id",is(2)))
        .andExpect(jsonPath("$[1].account",is("Account_Test1")))
        .andExpect(jsonPath("$[1].type",is("Type_Test1")))
        .andExpect(jsonPath("$[1].bidQuantity",is(20.0)));
  }

  @Test
  void givenNoBidListSavedWhenGetAllThenBidListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/bids")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void givenANewValidBidWhenCreateThenBidIsSavedAndStatus201() throws Exception {
    //Given
    when(service.create(any())).thenReturn(bid);

    //When
    mockMvc.perform(post("/api/bids")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{" +
            "\"account\":\"Account Test\","+
            "\"type\":\"Type Test\","+
            "\"bidQuantity\":10.0" +
            "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.account",is("Account Test")))
        .andExpect(jsonPath("$.type",is("Type Test")))
        .andExpect(jsonPath("$.bidQuantity",is(10.0)));
  }

  @Test
  void givenANewNotValidBidWhenCreateThenBidIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/api/bids")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"\","+
                "\"type\":\"\","+
                "\"bidQuantity\":" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenAExistingBidWhenUpdateThenBidIsSavedAndStatus201() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(bid));
    when(service.update(anyInt(),any())).thenReturn(bid);

    //When
    mockMvc.perform(put("/api/bids/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"Account Test\","+
                "\"type\":\"Type Test\","+
                "\"bidQuantity\":10.0" +
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.account",is("Account Test")))
        .andExpect(jsonPath("$.type",is("Type Test")))
        .andExpect(jsonPath("$.bidQuantity",is(10.0)));
  }

  @Test
  void givenANotValidBidWhenUpdateThenBidIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(put("/api/bids/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"\","+
                "\"type\":\"\","+
                "\"bidQuantity\":" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenANotExistBidWhenUpdateThenBidIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());

    //When
    mockMvc.perform(put("/api/bids/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"account\":\"test\","+
                "\"type\":\"test\","+
                "\"bidQuantity\":10.1" +
                "}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenAExistBidWhenDeleteThenBidIsDeletedAndStatus200() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(bid));
    //When
    mockMvc.perform(delete("/api/bids/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void givenANotExistBidWhenDeleteThenBidIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());
    //When
    mockMvc.perform(delete("/api/bids/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}