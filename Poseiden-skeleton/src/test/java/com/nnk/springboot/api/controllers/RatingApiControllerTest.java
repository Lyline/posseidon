package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
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

@WebMvcTest(RatingApiController.class)
class RatingApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RatingServiceImpl service;

  private final Rating rating= new Rating();
  private final Rating rating1= new Rating();

  @BeforeEach
  void setUp() {
    rating.setId(1);
    rating.setMoodysRating("mood_test");
    rating.setSandPRating("sand_test");
    rating.setFitchRating("fitch_test");
    rating.setOrderNumber(3);

    rating1.setId(2);
    rating1.setMoodysRating("mood_test1");
    rating1.setSandPRating("sand_test1");
    rating1.setFitchRating("fitch_test1");
    rating1.setOrderNumber(4);
  }

  @Test
  void givenTwoCurvesSavedWhenGetAllThenCurvesWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(rating,rating1));

    //When
    mockMvc.perform(get("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].id",is(1)))
        .andExpect(jsonPath("$[0].moodysRating",is("mood_test")))
        .andExpect(jsonPath("$[0].sandPRating",is("sand_test")))
        .andExpect(jsonPath("$[0].fitchRating",is("fitch_test")))
        .andExpect(jsonPath("$[0].orderNumber",is(3)))

        .andExpect(jsonPath("$[1].id",is(2)))
        .andExpect(jsonPath("$[1].moodysRating",is("mood_test1")))
        .andExpect(jsonPath("$[1].sandPRating",is("sand_test1")))
        .andExpect(jsonPath("$[1].fitchRating",is("fitch_test1")))
        .andExpect(jsonPath("$[1].orderNumber",is(4)));
  }

  @Test
  void givenNoCurveSavedWhenGetAllThenBidListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }
}