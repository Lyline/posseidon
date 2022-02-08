package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
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

@WebMvcTest(RatingController.class)
public class RatingControllerTest {

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
  void givenTwoRatingWhenRatingHomeThenRatingHomeTableWithTwoElementsDisplayed() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(rating,rating1));

    //When
    mockMvc.perform(get("/rating/list"))
        .andExpect(view().name("rating/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("mood_test")))
        .andExpect(content().string(containsString("sand_test")))
        .andExpect(content().string(containsString("fitch_test")))
        .andExpect(content().string(containsString("3")))

        .andExpect(content().string(containsString("2")))
        .andExpect(content().string(containsString("mood_test1")))
        .andExpect(content().string(containsString("sand_test1")))
        .andExpect(content().string(containsString("fitch_test1")))
        .andExpect(content().string(containsString("4")));
  }
}
