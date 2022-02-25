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

/**
 The type Rating web controller.
 */
@Controller
public class RatingWebController {

    private final RatingServiceImpl service;
    private final Logger logger= LoggerFactory.getLogger(RatingWebController.class);

    /**
     Instantiates a new Rating web controller.

     @param service the service
     */
    public RatingWebController(RatingServiceImpl service) {
        this.service = service;
    }

    /**
     Show the rating homepage with the list of the ratings.

     @param model the model

     @return the rating homepage with the list of ratings web page
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        List<Rating> ratings=service.getAll();

        logger.info("Read - All rating list : "+ratings.size()+" rating(s)");
        model.addAttribute("ratings",ratings);
        return "rating/list";
    }

    /**
     Show the add rating form.

     @param rating the rating
     @param model  the model

     @return the rating add form web page
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating, Model model) {
        model.addAttribute("rating", rating);
        return "rating/add";
    }

    /**
     Validate the creating rating.

     @param rating   the rating
     @param error   the error
     @param model    the model
     @param response the response

     @return the rating homepage with status 201 if the rating is created, or the add form with error message
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult error,
                           Model model, HttpServletResponse response) {
        if (error.hasErrors()){
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

    /**
     Show the update rating form.

     @param id    the id of the rating to update
     @param model the model

     @return the update rating form web page
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating=service.getById(id);

        logger.info("Read - rating : moody's rating ="+rating.getMoodysRating()+
        " , sand PRating= "+rating.getSandPRating()+" , fitch rating= "+ rating.getFitchRating()+
            " , order= "+ rating.getOrderNumber());
        model.addAttribute("rating",service.getById(id));
        return "rating/update";
    }

    /**
     Validate the update rating form.

     @param id     the id
     @param rating the rating
     @param result the result
     @param model  the model

     @return the rating homepage if the rating is updated, or the rating update form with the error message
     */
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

    /**
     Delete rating.

     @param id    the id of the rating to delete
     @param model the model

     @return the rating homepage when the rating is deleted
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - rating with id "+id+" is deleted");
        model.addAttribute("ratings",service.getAll());
        return "rating/list";
    }
}
