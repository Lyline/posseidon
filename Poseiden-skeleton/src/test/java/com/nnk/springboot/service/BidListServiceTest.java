package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BidListServiceTest {
  private BidListRepository repository=mock(BidListRepository.class);

  private BidListService classUnderTest= new BidListServiceImpl(repository);

  private final BidList bid= new BidList();
  private final BidList bid1= new BidList();
  private final BidList bidToSave= new BidList();

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

    bidToSave.setAccount("Account Test");
    bidToSave.setType("Type Test");
    bidToSave.setBidQuantity(10);
  }

  @Test
  void givenTwoBidListWhenGetAllThenReturnTwoResults() {
    //Given
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
    when(repository.save(any())).thenReturn(bid);

    //When
    BidList actual=classUnderTest.create(bidToSave);

    //Then
    assertSame(actual,bid);
    verify(repository, times(1)).save(bidToSave);
  }

  @Test
  void givenAExistingCurvePointWhenGetByIdThenCurveIsFounded() {
    //Given
    when(repository.getById(anyInt())).thenReturn(bid);

    //When
    BidList actual= classUnderTest.getById(1);

    //Then
    assertSame(actual,bid);
    verify(repository,times(1)).getById(1);
  }

  @Test
  void givenABidListWhenUpdateThenTheBidListIsUpdated() {
    //Given
    when(repository.save(any())).thenReturn(bid);

    //When
    BidList actual=classUnderTest.update(1,bidToSave);

    //Then
    assertSame(actual,bid);
    verify(repository, times(1)).save(bidToSave);
  }

  @Test
  void givenABidListWhenDeleteThenTheBidListIsDeleted() {
    //Given
    //When
    classUnderTest.delete(1);

    //Then
    verify(repository,times(1)).deleteById(1);
  }
}
