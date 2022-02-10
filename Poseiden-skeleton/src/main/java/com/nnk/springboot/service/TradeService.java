package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {
  List<Trade>getAll();
  Trade create(Trade trade);
  Trade getById(Integer id);
  Trade update(Integer id, Trade tradeToSave);
}