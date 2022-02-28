package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class RatingServiceTest {
  private final RatingRepository repository= mock(RatingRepository.class);
  private final RatingService classUnderTest= new RatingService(repository);

  private final Rating rating= new Rating();
  private final Rating rating1= new Rating();
  private final Rating ratingToSave= new Rating();

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

    ratingToSave.setMoodysRating("mood_test");
    ratingToSave.setSandPRating("sand_test");
    ratingToSave.setFitchRating("fitch_test");
    ratingToSave.setOrderNumber(3);

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

  @Test
  void givenANewRatingWhenCreateThenRatingIsSaved() {
    //Given
    when(repository.save(any())).thenReturn(rating);

    //When
    Rating actual= classUnderTest.create(ratingToSave);

    //Then
    assertSame(actual,rating);
  }

  @Test
  void givenAExistingRatingWhenGetByIdThenRatingIsFounded() {
    //Given
    when(repository.getById(anyInt())).thenReturn(rating);

    //When
    Rating actual= classUnderTest.getById(1);

    //Then
    assertSame(actual,rating);
    verify(repository,times(1)).getById(1);
  }

  @Test
  void givenAExistingRatingWhenUpdateThenRatingIsUpdated() {
    //Given
    when(repository.save(any())).thenReturn(rating);

    //When
    Rating actual= classUnderTest.update(1,ratingToSave);

    //Then
    assertSame(actual,rating);
    verify(repository,times(1)).save(ratingToSave);
  }

  @Test
  void givenAExistingRatingWhenDeleteByIdThenRatingIsDeleted() {
    //When
    classUnderTest.delete(1);
    //Then
    verify(repository,times(1)).deleteById(1);
  }

  @Test
  void givenAExistRatingWhenFindByIdThenRatingIsFound() {
    //Given
    when(repository.findById(anyInt())).thenReturn(Optional.of(rating));
    //When
    Optional<Rating>actual= classUnderTest.findById(1);

    //Then
    assertSame(actual.get(),rating);
  }

  @Test
  void givenANotExistRatingWhenFindByIdThenRatingIsEmpty() {
    //Given
    when(repository.findById(anyInt())).thenReturn(Optional.empty());
    //When
    Optional<Rating>actual= classUnderTest.findById(1);

    //Then
    assertTrue(actual.isEmpty());
  }
}