package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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

/**
 The type Rule name web controller.
 */
@Controller
public class RuleNameWebController {

    @Autowired
    private RuleNameService service;

    private final Logger logger= LoggerFactory.getLogger(RuleNameWebController.class);

    /**
     Instantiates a new Rule name web controller.

     @param service the service
     */
    public RuleNameWebController(RuleNameService service) {
        this.service = service;
    }

    /**
     Show the rule name homepage with the list of rule names.

     @param model the model

     @return the rule name homepage with the list of rule names web page
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model){
        List<RuleName>ruleNames= service.getAll();

        logger.info("Read - all rule name list : "+ruleNames.size()+" rule name(s) ");
        model.addAttribute("ruleNames",ruleNames);
        return "ruleName/list";
    }

    /**
     Show the add rule name form.

     @param ruleName the rule name
     @param model    the model

     @return the rule name add form web page
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName, Model model) {
        model.addAttribute("ruleName", ruleName);
        return "ruleName/add";
    }

    /**
     Validate the rule name creating.

     @param ruleName the rule name
     @param error   the error
     @param model    the model
     @param response the response

     @return the rule name homepage with status 201 if the rule name is created, or the add form with error message
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult error,
                           Model model, HttpServletResponse response) {
        if (error.hasErrors()){
            return "ruleName/add";
        }
        service.create(ruleName);
        response.setStatus(HttpServletResponse.SC_CREATED);

        logger.info("Create - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription()+" is saved");
        model.addAttribute("ruleNames", service.getAll());
        return "ruleName/list";
    }

    /**
     Show the rule name update form.

     @param id    the id of the rule name to update
     @param model the model

     @return the update rule name form web page
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {

        RuleName ruleName= service.getById(id);

        logger.info("Read - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription());
        model.addAttribute("ruleName",ruleName);
        return "ruleName/update";
    }

    /**
     Validate the update rule name form.

     @param id       the id
     @param ruleName the rule name
     @param error    the error
     @param model    the model

     @return the rule name homepage if the rule name is updated, or the rule name update form with the error message
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult error, Model model) {
       if(error.hasErrors()){
            return "ruleName/update";
        }

        service.update(id,ruleName);

        logger.info("Update - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription()+" is saved");
        model.addAttribute("ruleNames",service.getAll());
        return "ruleName/list";
    }

    /**
     Delete rule name.

     @param id    the id
     @param model the model

     @return the rule name homepage when the rule name is deleted
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        service.delete(id);

        logger.info("Delete - Rule name with id "+id+" is deleted");
        model.addAttribute("ruleNames",service.getAll());
        return "ruleName/list";
    }
}
