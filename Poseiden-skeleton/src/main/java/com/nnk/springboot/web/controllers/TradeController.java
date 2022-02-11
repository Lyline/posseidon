package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeServiceImpl;
import com.nnk.springboot.web.controllers.dto.TradeDTO;
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
    public String addTrade(Trade trade, Model model) {
        model.addAttribute("trade",trade);
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result,
                           Model model, HttpServletResponse response) {
        if (result.hasErrors()){
            return "trade/add";
        }

        service.create(trade);
        response.setStatus(HttpServletResponse.SC_CREATED);

        logger.info("Create - Trade : Account ="+trade.getAccount()+", Type ="
            + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity()+" is saved");
        model.addAttribute("trades",service.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade= service.getById(id);

        logger.info("Read - Trade : Account ="+trade.getAccount()+", Type ="
            + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity());
        model.addAttribute("trade",trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "trade/update";
        }

        Trade tradeUpdate= service.update(id,trade);

        logger.info("Update - Trade : Account ="+tradeUpdate.getAccount()+", Type ="
            + tradeUpdate.getType()+", Buy quantity ="+ tradeUpdate.getBuyQuantity()+" is saved");
        model.addAttribute("trades",service.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - Trade with id "+id+" is deleted");
        model.addAttribute("trades",service.getAll());
        return "trade/list";
    }
}
