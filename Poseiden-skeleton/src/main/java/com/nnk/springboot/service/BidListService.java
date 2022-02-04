package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BidListService {

  List<BidList> getAll();
  BidList create(BidList bidToSave);
  Optional<BidList> getBidListById(Integer id);
  BidList update(BidList bidToUpdate);
}
