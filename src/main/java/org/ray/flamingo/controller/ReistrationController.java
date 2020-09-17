package org.ray.flamingo.controller;

import javax.validation.Valid;

import org.ray.flamingo.form.UserForm;
import org.ray.flamingo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class ReistrationController {
	
	@Autowired
	private UserService us;
	
	@GetMapping
	public String registerForm(Model model) {
		model.addAttribute("form", new UserForm());
		
		return "register";
	}
	
	@PostMapping
	public String processRegistration(@Valid @ModelAttribute("form") UserForm form, Errors errors) {
		if(errors.hasErrors())
			return "register";
		
		if(us.tryRegister(form))
			return "redirect:/login";
		
		return "register";
	}

}
