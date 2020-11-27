package MidtermAMohamed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import MidtermAMohamed.database.DatabaseAccess;

@Controller
public class HomeController {
	
	
	
	
	
	@Lazy
	@Autowired
	private DatabaseAccess da;
	
	

	@GetMapping("/")
	public String index() {
		return "index";
	}


	@GetMapping("/secure")
	public String secureIndex() {
		return "/secure/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied()
	{
		return "/error/permission-denied";
	}

	@GetMapping("/register") 
	public String getRegister () 
	{ 
		return "register"; 
	}

	@PostMapping("/register")
	public String postRegister(@RequestParam String username,@RequestParam String password) {

		
		//Insert to the user to the DB
		da.addUser(username, password);
		
		// Find the userid for the new user
		Long userId = da.findUserAccount(username).getUserID(); 
		
		//Assign the new user to roles Admin and User
		da.addRole(userId, Long.valueOf(1));//Admin 
		da.addRole(userId, Long.valueOf(2));//user
		
		
		//Redirect to index page
		
		return"redirect:/";
		
		
	}
	
	
	
}
