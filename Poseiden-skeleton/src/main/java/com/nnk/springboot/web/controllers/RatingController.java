package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingServiceImpl;
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

@Controller
public class RatingController {

    private final RatingServiceImpl service;
    private final Logger logger= LoggerFactory.getLogger(RatingController.class);

    public RatingController(RatingServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        List<Rating> ratings=service.getAll();

        logger.info("Read - All rating list : "+ratings.size()+" rating(s)");
        model.addAttribute("ratings",ratings);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result,
                           Model model, HttpServletResponse response) {
        if (result.hasErrors()){
            return "rating/add";
        }

        service.create(rating);
        response.setStatus(HttpServletResponse.SC_CREATED);

        logger.info("Create - rating : moody's rating ="+rating.getMoodysRating()+
            " , sand PRating= "+rating.getSandPRating()+" , fitch rating= "+ rating.getFitchRating()+
            " , order= "+ rating.getOrderNumber()+" is saved");
        model.addAttribute("ratings",service.getAll());
        return "rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating=service.getById(id);

        logger.info("Read - rating : moody's rating ="+rating.getMoodysRating()+
        " , sand PRating= "+rating.getSandPRating()+" , fitch rating= "+ rating.getFitchRating()+
            " , order= "+ rating.getOrderNumber());
        model.addAttribute("rating",service.getById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
       if (result.hasErrors()){
           return "rating/update";
       }
        service.update(id,rating);

        logger.info("Update - rating : moody's rating ="+rating.getMoodysRating()+
            " , sand PRating= "+rating.getSandPRating()+" , fitch rating= "+ rating.getFitchRating()+
            " , order= "+ rating.getOrderNumber()+" is saved");
        model.addAttribute("ratings",service.getAll());
        return "rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - rating with id "+id+" is deleted");
        model.addAttribute("ratings",service.getAll());
        return "rating/list";
    }
}
