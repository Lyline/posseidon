package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 The interface bid service.
 */
@Service
public interface BidListService {

  /**
   Gets all bids.

   @return the list of bids
   */
  List<BidList> getAll();

  /**
   Create bid.

   @param bidToSave the bid to save

   @return the bid object created
   */
  BidList create(BidList bidToSave);

  /**
   Gets bid by id.

   @param id the id of the bid

   @return the bid object if it exists or null
   */
  BidList getById(Integer id);

  /**
   Find bid by id.

   @param id the id of the bid

   @return the optional, return bid object if it exists or optional is empty
   */
  Optional<BidList> findById(Integer id);

  /**
   Update bid by id.

   @param id          the id of the bid
   @param bidToUpdate the bid to update

   @return the bid updated
   */
  BidList update(Integer id, BidList bidToUpdate);

  /**
   Delete bid by id.

   @param id the id of the bid
   */
  void delete(Integer id);
}
