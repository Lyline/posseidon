package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 The interface Trade service.
 */
@Service
public interface TradeService {
  /**
   Get all trades.

   @return the list of the trades
   */
  List<Trade>getAll();

  /**
   Create trade.

   @param trade the trade

   @return the trade object created
   */
  Trade create(Trade trade);

  /**
   Gets trade by id.

   @param id the id of the trade

   @return the trade object if it exists or null
   */
  Trade getById(Integer id);

  /**
   Find trade by id.

   @param id the id of the trade

   @return the optional, return trade object if it exists or optional empty
   */
  Optional<Trade> findById(Integer id);

  /**
   Update trade by id.

   @param id          the id of the trade
   @param tradeToSave the trade to save

   @return the trade object updated
   */
  Trade update(Integer id, Trade tradeToSave);

  /**
   Delete trade by id.

   @param id the id of the trade
   */
  void delete(Integer id);
}
