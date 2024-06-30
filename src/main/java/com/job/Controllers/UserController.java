package com.job.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.job.Entity.User;
import com.job.Repository.UserRepo;




@Controller
@RequestMapping("/user")
public class UserController {

	// @Autowired
	// private UserRepo userRepo;

	// @ModelAttribute
	// public void commonUser(Principal p, Model m) {
	// 	if (p != null) {
	// 		String email = p.getName();
	// 		User user = userRepo.findByEmail(email);
	// 		m.addAttribute("user", user);
	// 	}

	// }

	@RequestMapping(value = "/carrer")
	public String Carrer() {
   
	   System.out.println("Dashboard page is running.....");
		return"user/carrer";
	}
}
