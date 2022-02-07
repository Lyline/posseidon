package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.dto.BidListDTO;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListServiceImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<BidListDTO>bidsDTO=new ArrayList<>();
        List<BidList>bids=service.getAll();

        for (BidList bid:bids) {
            BidListDTO bidDTO=mapper.map(bid,BidListDTO.class);
            bidsDTO.add(bidDTO);
        }

      logger.info("Get - all bid list : "+bids.size()+" bid(s)");
      model.addAttribute("bids",bidsDTO);
      return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid,Model model) {
      model.addAttribute("bidList",bid);
      return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result,
                           HttpServletResponse response, Model model) {

      if (result.hasErrors()){
        return "bidList/add";
      }

      List<BidListDTO>bidsDTO= new ArrayList<>();

      service.create(bid);
      response.setStatus(HttpServletResponse.SC_CREATED);

      List<BidList>bids=service.getAll();

      for (BidList bidToDTO:bids) {
        BidListDTO bidMapDTO=mapper.map(bidToDTO,BidListDTO.class);
        bidsDTO.add(bidMapDTO);
      }
      logger.info("Post - bid list : Account ="+bid.getAccount()+", Type ="
          +bid.getType()+", Bid quantity ="+bid.getBidQuantity()+" is saved");
      model.addAttribute("bids",bidsDTO);
      return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
      Optional<BidList> bidList=service.getBidListById(id);

      model.addAttribute("bidList", bidList.get());
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model, HttpServletResponse response) {
      if (result.hasErrors()){
        return "/bidList/update";
      }else response.setStatus(HttpServletResponse.SC_CREATED);

      BidList bidListToUpdate= new BidList();
      bidListToUpdate.setBidListId(id);
      bidListToUpdate.setAccount(bidList.getAccount());
      bidListToUpdate.setType(bidList.getType());
      bidListToUpdate.setBidQuantity(bidList.getBidQuantity());

      BidList bidListUpdated=service.update(bidListToUpdate);

      logger.info("Update - Bid list id ="+id+", Account ="+bidListToUpdate.getAccount()
          +", Type ="+bidListToUpdate.getType()+", Bid quantity ="
          +bidListToUpdate.getBidQuantity()+" is updated");

      model.addAttribute("bidList",bidListUpdated);
      model.addAttribute("bids",service.getAll());
      return "/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

      model.addAttribute("bids",service.getAll());
        return "/bidList/list";
    }
}
