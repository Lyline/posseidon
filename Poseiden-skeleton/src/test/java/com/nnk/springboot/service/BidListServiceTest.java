package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
  }
}
