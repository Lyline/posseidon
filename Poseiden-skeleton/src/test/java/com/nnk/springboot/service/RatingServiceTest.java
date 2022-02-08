package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatingServiceTest {
  private final RatingRepository repository= mock(RatingRepository.class);
  private final RatingService classUnderTest= new RatingServiceImpl(repository);

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
  void givenTwoRatingWhenGetAllThenReturnListOfRating() {
    //Given
    when(repository.findAll()).thenReturn(List.of(rating,rating1));

    //When
    List<Rating> actual= classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
  }
}