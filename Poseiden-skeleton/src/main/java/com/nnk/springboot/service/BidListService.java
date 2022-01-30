package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BidListService {

  List<BidList> getAll();
  BidList create(BidList bidToSave);
}
