package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import com.nnk.springboot.web.controllers.dto.TradeDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 The type Trade web controller.
 */
@Controller
public class TradeWebController {

    @Autowired
    private TradeService service;

    private final ModelMapper mapper= new ModelMapper();
    private final Logger logger= LoggerFactory.getLogger(TradeWebController.class);

    /**
     Instantiates a new Trade web controller.

     @param service the service
     */
    public TradeWebController(TradeService service) {
        this.service = service;
    }

    /**
     Show trade homepage with the list of trades.

     @param model the model

     @return the trade homepage with the list of trades web page
     */
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

    /**
     Show the add trade form.

     @param trade the trade
     @param model the model

     @return the trade add form web page
     */
    @GetMapping("/trade/add")
    public String addTrade(Trade trade, Model model) {
        model.addAttribute("trade",trade);
        return "trade/add";
    }

    /**
     Validate the trade creating.

     @param trade    the trade
     @param error   the error
     @param model    the model
     @param response the response

     @return the trade homepage with status 201 if the trade is created, or the add form with error message
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult error,
                           Model model, HttpServletResponse response) {
        if (error.hasErrors()){
            return "trade/add";
        }

        service.create(trade);
        response.setStatus(HttpServletResponse.SC_CREATED);

        logger.info("Create - Trade : Account ="+trade.getAccount()+", Type ="
            + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity()+" is saved");
        model.addAttribute("trades",service.getAll());
        return "trade/list";
    }

    /**
     Show the trade update form.

     @param id    the id
     @param model the model

     @return the update trade form web page
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade= service.getById(id);

        logger.info("Read - Trade : Account ="+trade.getAccount()+", Type ="
            + trade.getType()+", Buy quantity ="+ trade.getBuyQuantity());
        model.addAttribute("trade",trade);
        return "trade/update";
    }

    /**
     Update trade string.

     @param id     the id of the trade
     @param trade  the trade
     @param error  the error
     @param model  the model

     @return the trade homepage if the trade is updated, or the trade update form with the error message
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult error, Model model) {
        if (error.hasErrors()){
            return "trade/update";
        }

        Trade tradeUpdate= service.update(id,trade);

        logger.info("Update - Trade : Account ="+tradeUpdate.getAccount()+", Type ="
            + tradeUpdate.getType()+", Buy quantity ="+ tradeUpdate.getBuyQuantity()+" is saved");
        model.addAttribute("trades",service.getAll());
        return "trade/list";
    }

    /**
     Delete trade.

     @param id    the id
     @param model the model

     @return the trade homepage when the trade is deleted
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - Trade with id "+id+" is deleted");
        model.addAttribute("trades",service.getAll());
        return "trade/list";
    }
}
