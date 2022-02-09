package com.nnk.springboot.controllers;

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

        logger.info("Get - all rule name list : "+ruleNames.size()+" rule name(s) ");
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

        logger.info("Post - Rule name : Name ="+ruleName.getName()+", Description ="
            +ruleName.getDescription()+" is saved");
        model.addAttribute("ruleNames", service.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        return "redirect:/ruleName/list";
    }
}
