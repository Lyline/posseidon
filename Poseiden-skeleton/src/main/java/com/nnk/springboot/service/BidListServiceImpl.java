package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {
  private final BidListRepository repository;
  public BidListServiceImpl(BidListRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<BidList> getAll() {
    return repository.findAll();
  }
}
