package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BidListServiceImpl implements BidListService {
  private final BidListRepository repository;
  public BidListServiceImpl(BidListRepository repository) {
    this.repository = repository;
  }

  @Override
  @Transactional
  public List<BidList> getAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public BidList create(BidList bidToSave) {
    return repository.save(bidToSave);
  }

  @Override
  @Transactional
  public BidList getById(Integer id) {
    return repository.getById(id);
  }

  @Override
  @Transactional
  public BidList update(Integer id, BidList bidToUpdate) {
    bidToUpdate.setId(id);
    return repository.save(bidToUpdate);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
