package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
class RatingApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RatingService service;

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
  void givenTwoRatingsSavedWhenGetAllThenRatingsListWithTwoResultsAndStatus200() throws Exception {
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
  void givenNoRatingSavedWhenGetAllThenRatingsListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void givenANewValidRatingWhenCreateThenRatingIsSavedAndStatus201() throws Exception {
    //Given
    when(service.create(any())).thenReturn(rating);

    //When
    mockMvc.perform(post("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"moodysRating\":\"mood_test\","+
                "\"sandPRating\":\"sand_test\","+
                "\"fitchRating\":\"fitch_test\"," +
                "\"orderNumber\":3" +
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.moodysRating",is("mood_test")))
        .andExpect(jsonPath("$.sandPRating",is("sand_test")))
        .andExpect(jsonPath("$.fitchRating",is("fitch_test")))
        .andExpect(jsonPath("$.orderNumber",is(3)));
  }

  @Test
  void givenANewNotValidRatingWhenCreateThenRatingIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/api/ratings")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"moodysRating\":\"\","+
                "\"sandPRating\":\"\","+
                "\"fitchRating\":\"\"," +
                "\"orderNumber\":" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenAExistingRatingWhenUpdateThenRatingIsSavedAndStatus201() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(rating));
    when(service.update(anyInt(),any())).thenReturn(rating);

    //When
    mockMvc.perform(put("/api/ratings/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"moodysRating\":\"mood_test\","+
                "\"sandPRating\":\"sand_test\","+
                "\"fitchRating\":\"fitch_test\"," +
                "\"orderNumber\":3" +
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.moodysRating",is("mood_test")))
        .andExpect(jsonPath("$.sandPRating",is("sand_test")))
        .andExpect(jsonPath("$.fitchRating",is("fitch_test")))
        .andExpect(jsonPath("$.orderNumber",is(3)));
  }

  @Test
  void givenANotValidRatingWhenUpdateThenRatingIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(put("/api/ratings/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"moodysRating\":\"\","+
                "\"sandPRating\":\"\","+
                "\"fitchRating\":\"\"," +
                "\"orderNumber\":" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenANotExistRatingWhenUpdateThenRatingIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());

    //When
    mockMvc.perform(put("/api/ratings/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"moodysRating\":\"mood_test\","+
                "\"sandPRating\":\"sand_test\","+
                "\"fitchRating\":\"fitch_test\"," +
                "\"orderNumber\":3" +
                "}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenAExistRatingWhenDeleteThenRatingIsDeletedAndStatus200() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(rating));
    //When
    mockMvc.perform(delete("/api/ratings/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void givenANotExistRatingWhenDeleteThenRatingIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());
    //When
    mockMvc.perform(delete("/api/ratings/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}