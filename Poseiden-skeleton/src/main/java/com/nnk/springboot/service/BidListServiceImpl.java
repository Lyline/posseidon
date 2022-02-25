package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 The type bid service implementation.
 */
@Service
public class BidListServiceImpl implements BidListService {
  private final BidListRepository repository;

  /**
   Instantiates a new Bid list service.

   @param repository the repository
   */
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
  public Optional<BidList> findById(Integer id){
    return repository.findById(id);
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
