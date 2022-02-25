package com.nnk.springboot.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 The type Home web controller.
 */
@Controller
public class HomeController
{

	/**
	 Show homepage of web application.

	 @return the homepage of web application
	 */
	@RequestMapping("/")
	public String home()
	{
		return "home";
	}

}
