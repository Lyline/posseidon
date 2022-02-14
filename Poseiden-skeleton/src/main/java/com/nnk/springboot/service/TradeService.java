package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TradeService {
  List<Trade>getAll();
  Trade create(Trade trade);
  Trade getById(Integer id);
  Optional<Trade> findById(Integer id);
  Trade update(Integer id, Trade tradeToSave);
  void delete(Integer id);
}
