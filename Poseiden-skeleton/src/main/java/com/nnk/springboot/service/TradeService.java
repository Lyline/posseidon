package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TradeService {
  List<Trade>getAll();
}
