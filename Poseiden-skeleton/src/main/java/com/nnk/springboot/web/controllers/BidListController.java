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

@Controller
public class BidListController {
   private final BidListServiceImpl service;

    private final ModelMapper mapper=new ModelMapper();
    private final Logger logger= LoggerFactory.getLogger(BidListController.class);

    public BidListController(BidListServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model, HttpServletResponse response){
      List<BidList>bids=service.getAll();

      List<BidListDTO>bidsDTO=bids.stream()
              .map(bid->mapper.map(bid,BidListDTO.class))
              .collect(Collectors.toList());

      logger.info("Read - all bid list : "+bids.size()+" bid(s)");
      model.addAttribute("bids",bidsDTO);
      return "/bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid,Model model) {
      model.addAttribute("bidList",bid);
      return "/bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result,
                           Model model, HttpServletResponse response) {
      if (result.hasErrors()){
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

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
      BidList bid= service.getById(id);

      logger.info("Read - bid list : Account ="+bid.getAccount()+", Type ="
          +bid.getType()+", Bid quantity ="+bid.getBidQuantity());
      model.addAttribute("bidList", bid);
        return "/bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model, HttpServletResponse response) {
      if (result.hasErrors()){
        return "redirect:"+id;
      }else response.setStatus(HttpServletResponse.SC_CREATED);

      service.update(id,bidList);

      logger.info("Update - bid list : Account ="+bidList.getAccount()
          +", Type ="+bidList.getType()+", Bid quantity ="
          +bidList.getBidQuantity()+" is saved");
      model.addAttribute("bids",service.getAll());
      return "/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
      service.delete(id);

      logger.info("Delete - bid list with id "+id+" is deleted");
      model.addAttribute("bids",service.getAll());
        return "/bidList/list";
    }
}
