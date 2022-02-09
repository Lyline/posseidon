package com.nnk.springboot.controllers;

import com.nnk.springboot.controllers.dto.TradeDTO;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TradeController {
    private final TradeServiceImpl service;

    private final ModelMapper mapper= new ModelMapper();
    private final Logger logger= LoggerFactory.getLogger(TradeController.class);

    public TradeController(TradeServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/trade/list")
    public String home(Model model){
        List<Trade> trades= service.getAll();

        List<TradeDTO>tradesDto= trades.stream()
            .map(trade->mapper.map(trade,TradeDTO.class))
            .collect(Collectors.toList());

        logger.info("Read - all trade list : "+trades.size()+" trade(s)");
        model.addAttribute("trades",tradesDto);
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade and return Trade list
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        return "redirect:/trade/list";
    }
}
