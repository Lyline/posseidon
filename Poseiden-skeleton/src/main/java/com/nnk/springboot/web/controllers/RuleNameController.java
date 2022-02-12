package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameServiceImpl;
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
public class RuleNameController {
    private final RuleNameServiceImpl service;
    private final Logger logger= LoggerFactory.getLogger(RuleNameController.class);

    public RuleNameController(RuleNameServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model){
        List<RuleName>ruleNames= service.getAll();

        logger.info("Read - all rule name list : "+ruleNames.size()+" rule name(s) ");
        model.addAttribute("ruleNames",ruleNames);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result,
                           Model model, HttpServletResponse response) {
        if (result.hasErrors()){
            return "ruleName/add";
        }
        service.create(ruleName);
        response.setStatus(HttpServletResponse.SC_CREATED);

        logger.info("Create - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription()+" is saved");
        model.addAttribute("ruleNames", service.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName= service.getById(id);

        logger.info("Read - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription());
        model.addAttribute("ruleName",ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
       if(result.hasErrors()){
            return "ruleName/update";
        }

        service.update(id,ruleName);

        logger.info("Update - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription()+" is saved");
        model.addAttribute("ruleNames",service.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - Rule name with id "+id+" is deleted");
        model.addAttribute("ruleNames",service.getAll());
        return "ruleName/list";
    }
}