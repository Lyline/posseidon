package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BidListServiceTest {
  private BidListRepository repository=mock(BidListRepository.class);

  private BidListService classUnderTest= new BidListServiceImpl(repository);

  @Test
  void givenTwoBidListWhenGetAllThenReturnTwoResults() {
    //Given
    BidList bid= new BidList("Account Test", "Type Test", 10d);
    BidList bid1= new BidList("Account Test 1", "Type Test", 20d);

    when(repository.findAll()).thenReturn(List.of(bid,bid1));

    //When
    List<BidList> actual=classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
    verify(repository,times(1)).findAll();
  }

  @Test
  void givenANewBidListWhenCreateThenTheBidListIsSaved() {
    //Given
    BidList bidToSave= new BidList("Account Test", "Type Test", 10d);
    BidList bid= new BidList("Account Test", "Type Test", 10d);
    bid.setBidListId(1);

    when(repository.save(any())).thenReturn(bid);

    //When
    BidList actual=classUnderTest.create(bidToSave);

    //Then
    assertSame(actual,bid);
    verify(repository, times(1)).save(bidToSave);
  }

  @Test
  void givenABidListWhenUpdateThenTheBidListIsUpdated() {
    //Given
    BidList bidToUpdate= new BidList("Account Test", "Type Test", 10d);
    BidList bid= new BidList("Account Test", "Type Test", 10d);
    bid.setBidListId(1);

    when(repository.save(any())).thenReturn(bid);

    //When
    BidList actual=classUnderTest.update(bidToUpdate);

    //Then
    assertSame(actual,bid);
    verify(repository, times(1)).save(bidToUpdate);
  }
}
