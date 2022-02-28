package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.UserService;
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
import java.util.Optional;
import java.util.regex.Pattern;

/**
 The type User web controller.
 */
@Controller
public class UserWebController {

    @Autowired
    private UserService service;

    private final Logger logger= LoggerFactory.getLogger(UserWebController.class);

    private static final String PASSWORD_REGEX =
        "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#!$%^&+=]).{8,16}$";

    private static final Pattern PASSWORD_PATTERN =
        Pattern.compile(PASSWORD_REGEX);

    private boolean passwordInvalid= false;

    /**
     Instantiates a new User web controller.

     @param service the service
     */
    public UserWebController(UserService service) {
        this.service = service;
    }

    /**
     Show user homepage with the list of users.

     @param model the model

     @return the user homepage with the list of users web page
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        List<User>users=service.getAll();

        logger.info("Read - all user list : "+users.size()+" user(s)");
        model.addAttribute("users", users);
        return "user/list";
    }

    /**
     Show the add user form.

     @param user  the user
     @param model the model

     @return the user add form web page
     */
    @GetMapping("/user/add")
    public String addUser(User user, Model model) {

        model.addAttribute("passwordInvalid",passwordInvalid);
        model.addAttribute("user",user);
        return "user/add";
    }

    /**
     Validate the user creating.

     @param user     the user
     @param error    the error
     @param model    the model
     @param response the response

     @return the user homepage with status 201 if the user is created, or the add form with error message
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult error,
                           Model model,HttpServletResponse response) {

        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            passwordInvalid=true;
            model.addAttribute("passwordInvalid", passwordInvalid);
            return "user/add";
        }

        if (!error.hasErrors()) {
            passwordInvalid=false;
            service.create(user);
            response.setStatus(HttpServletResponse.SC_CREATED);
            logger.info("Create - user: full name= "+user.getFullName()+", username= "+ user.getUsername()+
                ", role= "+user.getRole()+" is saved");

            model.addAttribute("passwordInvalid", passwordInvalid);
            model.addAttribute("users", service.getAll());

            return "redirect:/user/list";
        }
        return "user/add";
    }

    /**
     Show the user update form.

     @param id    the id of the user
     @param model the model

     @return the update user form web page
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        user.setPassword("");

        logger.info("Read - user: full name= "+user.getFullName()+" , username= "+ user.getUsername());
        model.addAttribute("user", user);
        return "user/update";
    }

    /**
     Update user string.

     @param id     the id of the user
     @param user   the user
     @param error  the error
     @param model  the model

     @return the user homepage if the user is updated, or the user update form with the error message
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid User user,
                             BindingResult error, Model model) {
        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            passwordInvalid=true;
            model.addAttribute("passwordInvalid", passwordInvalid);
            return "user/update";
        }

        if (error.hasErrors()) {
            return "user/update";
        }

        service.update(id,user);

        passwordInvalid=false;

        logger.info("Update - user: full name= "+user.getFullName()+", username= "+ user.getUsername()+
            ", role= "+user.getRole()+" is saved");
        model.addAttribute("passwordInvalid", passwordInvalid);
        model.addAttribute("users", service.getAll());
        return "redirect:/user/list";
    }

    /**
     Delete user.

     @param id    the id of the user
     @param model the model

     @return the user homepage when the user is deleted
     */
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
