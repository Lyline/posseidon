package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService{

  private final TradeRepository repository;

  public TradeServiceImpl(TradeRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public List<Trade> getAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Trade create(Trade trade) {
    return repository.save(trade);
  }
}
