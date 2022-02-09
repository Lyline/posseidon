package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TradeServiceTest {
  private TradeRepository repository= mock(TradeRepository.class);
  private TradeService classUnderTest= new TradeServiceImpl(repository);

  private final Trade trade= new Trade();
  private final Trade trade1= new Trade();
  private final Trade tradeToSave= new Trade();

  @BeforeEach
  void setUp() {
    trade.setTradeId(1);
    trade.setAccount("Account_test");
    trade.setType("Type_test");
    trade.setBuyQuantity(10);

    trade.setTradeId(2);
    trade.setAccount("Account_test1");
    trade.setType("Type_test1");
    trade.setBuyQuantity(20);

    tradeToSave.setAccount("Account_test");
    tradeToSave.setType("Type_test");
    tradeToSave.setBuyQuantity(10);
  }

  @Test
  void givenTwoTradesWhenGetAllThenReturnListOfTrade() {
    //Given
    when(repository.findAll()).thenReturn(List.of(trade,trade1));

    //When
    List<Trade>actual= classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
  }

  @Test
  void givenANewTradeWhenCreateThenTradeIsSaved() {
    //Given
    when(repository.save(any())).thenReturn(trade);

    //When
    Trade actual= classUnderTest.create(tradeToSave);

    //Then
    assertSame(actual,trade);
  }
}