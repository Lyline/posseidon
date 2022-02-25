package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 The type Trade service implementation.
 */
@Service
public class TradeServiceImpl implements TradeService{

  private final TradeRepository repository;

  /**
   Instantiates a new Trade service.

   @param repository the repository
   */
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

  @Override
  @Transactional
  public Trade getById(Integer id) {
    return repository.getById(id);
  }

  @Override
  @Transactional
  public Optional<Trade> findById(Integer id) {
    return repository.findById(id);
  }

  @Override
  @Transactional
  public Trade update(Integer id, Trade tradeToSave) {
    tradeToSave.setTradeId(id);
    return repository.save(tradeToSave);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
