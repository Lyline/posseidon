package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
import com.nnk.springboot.web.controllers.dto.BidListDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 The type Bid web controller.
 */
@Controller
public class BidListWebController {
   private final BidListServiceImpl service;

    private final ModelMapper mapper=new ModelMapper();
    private final Logger logger= LoggerFactory.getLogger(BidListWebController.class);

  /**
   Instantiates a new Bid web controller.

   @param service the service
   */
  public BidListWebController(BidListServiceImpl service) {
        this.service = service;
    }

  /**
   Show homepage of bid with the list of bids recorded.

   @param model    the model

   @return the bid homepage with the list of bids web page
   */
  @RequestMapping("/bidList/list")
    public String home(Model model){
      List<BidList>bids=service.getAll();

      List<BidListDTO>bidsDTO=bids.stream()
              .map(bid->mapper.map(bid,BidListDTO.class))
              .collect(Collectors.toList());

      logger.info("Read - all bid list : "+bids.size()+" bid(s)");
      model.addAttribute("bids",bidsDTO);
      return "/bidList/list";
    }

  /**
   Show bid form for create a new bid

   @param bid   the bid
   @param model the model

   @return the add form web page
   */
  @GetMapping("/bidList/add")
    public String addBidForm(BidList bid,Model model) {
      model.addAttribute("bidList",bid);
      return "/bidList/add";
    }

  /**
   Validate the creating bid.

   @param bid      the bid
   @param error    the error
   @param model    the model
   @param response the response

   @return the bid homepage with status 201 if the bid is created, or the add form with error message
   */
  @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult error,
                           Model model, HttpServletResponse response) {
      if (error.hasErrors()){
        return "/bidList/add";
      }else response.setStatus(HttpServletResponse.SC_CREATED);

      service.create(bid);

      List<BidList>bids=service.getAll();

      List<BidListDTO>bidsDTO=bids.stream()
          .map(bidDto->mapper.map(bidDto,BidListDTO.class))
          .collect(Collectors.toList());

      logger.info("Create - bid list : Account ="+bid.getAccount()+", Type ="
          +bid.getType()+", Bid quantity ="+bid.getBidQuantity()+" is saved");
      model.addAttribute("bids",bidsDTO);
      return "/bidList/list";
    }

  /**
   Show the bid update form.

   @param id    the id of the bid to update
   @param model the model

   @return the bid update form web page
   */
  @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
      BidList bid= service.getById(id);

      logger.info("Read - bid list : Account ="+bid.getAccount()+", Type ="
          +bid.getType()+", Bid quantity ="+bid.getBidQuantity());
      model.addAttribute("bidList", bid);
        return "/bidList/update";
    }

  /**
   Validate the update bid form.

   @param id       the id of the bid to update
   @param bidList  the bid to submit
   @param error    the error
   @param model    the model
   @param response the response

   @return the bid homepage with status 201 when the bid is updated, or the bid update form with the error message
   */
  @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult error, Model model, HttpServletResponse response) {
      if (error.hasErrors()){
        return "/bidList/update";
      }else response.setStatus(HttpServletResponse.SC_CREATED);

      service.update(id,bidList);

      logger.info("Update - bid list : Account ="+bidList.getAccount()
          +", Type ="+bidList.getType()+", Bid quantity ="
          +bidList.getBidQuantity()+" is saved");
      model.addAttribute("bids",service.getAll());
      return "redirect:/bidList/list";
    }

  /**
   Delete bid.

   @param id    the id of the bid to delete
   @param model the model

   @return the bid homepage when the bid is deleted
   */
  @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
      service.delete(id);

      logger.info("Delete - bid list with id "+id+" is deleted");
      model.addAttribute("bids",service.getAll());
        return "/bidList/list";
    }
}
