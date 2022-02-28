package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import com.nnk.springboot.web.controllers.dto.CurvePointDTO;
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
 The type Curve point web controller.
 */
@Controller
public class CurvePointWebController {

    @Autowired
    private CurvePointService service;

    private final ModelMapper mapper=new ModelMapper();
    private final Logger logger= LoggerFactory.getLogger(CurvePointWebController.class);

    /**
     Instantiates a new Curve point controller.

     @param service the service
     */
    public CurvePointWebController(CurvePointService service) {
        this.service = service;
    }

    /**
     Show homepage of curve point with the list of curve points.

     @param model the model

     @return the curve point homepage with le list of curve point web page
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        List<CurvePoint>curves= service.getAll();
        List<CurvePointDTO>curvesDTO=curves.stream()
            .map(curve->mapper.map(curve,CurvePointDTO.class))
            .collect(Collectors.toList());

        logger.info("Get - all curves point : "+curves.size()+" curve(s) point");
        model.addAttribute("curves",curvesDTO);
        return "curvePoint/list";
    }

    /**
     Show the add curve point form for create a new curve point.

     @param curvePoint the curve point
     @param model      the model

     @return the curve point add form web page
     */
    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    /**
     Validate the creating curve point.

     @param curvePoint the curve point
     @param error      the error
     @param model      the model
     @param response   the response

     @return the curve point homepage with status 201 if the curve point is created, or the add form with error message
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult error,
                           Model model, HttpServletResponse response) {
        if (error.hasErrors()){
            return "curvePoint/add";
        }

        service.create(curvePoint);
        response.setStatus(HttpServletResponse.SC_CREATED);

        List<CurvePoint>curves= service.getAll();
        List<CurvePointDTO>curvesDTO=curves.stream()
            .map(curve->mapper.map(curve,CurvePointDTO.class))
            .collect(Collectors.toList());

        logger.info("Create - curve point : curve point id= "+curvePoint.getCurveId()+
            ", term= "+ curvePoint.getTerm()+", value= "+ curvePoint.getValue()+" is saved");
        model.addAttribute("curves",curvesDTO);
        return "curvePoint/list";
    }

    /**
     Show curve point update form.

     @param id    the id of the curve point to update
     @param model the model

     @return the curve point update form web page
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curve=service.getById(id);

        model.addAttribute("curvePoint",curve);
        return "curvePoint/update";
    }

    /**
     Validate the curve point update form.

     @param id         the id of the curve point to update
     @param curvePoint the curve point
     @param error      the error
     @param model      the model

     @return the curve point homepage if the curve point is updated, or the curve point update form with the error message
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult error, Model model) {
        if (error.hasErrors()){
            return "curvePoint/update";
        }

        service.update(id,curvePoint);

        logger.info("Update - curve point : curve point id= "+curvePoint.getCurveId()+
            ", term= "+ curvePoint.getTerm()+", value= "+ curvePoint.getValue()+" is saved");
        model.addAttribute("curves",service.getAll());
        return "curvePoint/list";
    }

    /**
     Delete bid.

     @param id    the id of the curve point
     @param model the model

     @return the curve point homepage when the curve point is deleted
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - curve point with id "+id+" is deleted");
        model.addAttribute("curves",service.getAll());
        return "curvePoint/list";
    }
}
