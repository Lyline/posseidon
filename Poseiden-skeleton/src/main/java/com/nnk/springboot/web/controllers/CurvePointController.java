package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import com.nnk.springboot.web.controllers.dto.CurvePointDTO;
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
public class CurvePointController {
    // TODO: Inject Curve Point service

    private final CurvePointServiceImpl service;

    private final ModelMapper mapper=new ModelMapper();
    private final Logger logger= LoggerFactory.getLogger(CurvePointController.class);

    public CurvePointController(CurvePointServiceImpl service) {
        this.service = service;
    }

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

    @GetMapping("/curvePoint/add")
    public String addCurvePointForm(CurvePoint curvePoint, Model model) {
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result,
                           Model model, HttpServletResponse response) {
        if (result.hasErrors()){
            return "curvePoint/add";
        }

        service.create(curvePoint);
        response.setStatus(HttpServletResponse.SC_CREATED);

        List<CurvePoint>curves= service.getAll();
        List<CurvePointDTO>curvesDTO=curves.stream()
            .map(curve->mapper.map(curve,CurvePointDTO.class))
            .collect(Collectors.toList());

        model.addAttribute("curves",curvesDTO);
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curve=service.getById(id);

        model.addAttribute("curvePoint",curve);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        if (result.hasErrors()){
            return "curvePoint/update";
        }

        service.update(id,curvePoint);

        model.addAttribute("curves",service.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        service.delete(id);

        model.addAttribute("curves",service.getAll());
        return "curvePoint/list";
    }
}
