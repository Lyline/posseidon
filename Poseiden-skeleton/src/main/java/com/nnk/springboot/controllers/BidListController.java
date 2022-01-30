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

@Controller
public class BidListController {
   private final BidListServiceImpl service;

    private ModelMapper mapper=new ModelMapper();
    private Logger logger= LoggerFactory.getLogger(BidListController.class);

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

        if(bids.isEmpty()){
          response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }else response.setStatus(HttpServletResponse.SC_OK);

        model.addAttribute("bids",bidsDTO);
        logger.info("Get - all bid list : "+bids.size()+" bid(s)");
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
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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

      model.addAttribute("bids",bidsDTO);
      return "bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call com.nnk.springboot.service to update Bid and return list Bid
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        return "redirect:/bidList/list";
    }
}
