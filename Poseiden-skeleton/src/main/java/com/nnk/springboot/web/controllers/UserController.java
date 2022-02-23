package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserServiceImpl;
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
import java.util.Optional;

@Controller
public class UserController {

    private final UserServiceImpl service;
    private final Logger logger= LoggerFactory.getLogger(UserController.class);

    public UserController(UserServiceImpl service) {
        this.service = service;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        List<User>users=service.getAll();

        logger.info("Read = all user list : "+users.size()+" user(s)");
        model.addAttribute("users", users);
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(User user, Model model) {
        model.addAttribute("user",user);
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            service.create(user);

            logger.info("Create - user: full name= "+user.getFullName()+", username= "+ user.getUsername()+
                ", role= "+user.getRole()+" is saved");
            model.addAttribute("users", service.getAll());
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");

        logger.info("Read - user: full name= "+user.getFullName()+" , username= "+ user.getUsername());
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }

        service.update(id,user);

        logger.info("Update - user: full name= "+user.getFullName()+", username= "+ user.getUsername()+
            ", role= "+user.getRole()+" is saved");
        model.addAttribute("users", service.getAll());
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        Optional<User> user = service.findById(id);

        if (!user.isEmpty()) {
            service.delete(id);
        }else new IllegalArgumentException("Invalid user Id:" + id);

        logger.info("Delete - user with id "+id+" is deleted");
        model.addAttribute("users", service.getAll());
        return "redirect:/user/list";
    }
}
