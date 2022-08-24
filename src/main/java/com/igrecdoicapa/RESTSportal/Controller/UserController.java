package com.igrecdoicapa.RESTSportal.Controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igrecdoicapa.RESTSportal.Entity.Activity;
import com.igrecdoicapa.RESTSportal.Entity.Bundle;
import com.igrecdoicapa.RESTSportal.Entity.User;
import com.igrecdoicapa.RESTSportal.Service.BundleService;
import com.igrecdoicapa.RESTSportal.Service.UserService;
import com.igrecdoicapa.RESTSportal.email.EmailSenderService;


@RestController
@RequestMapping("/api")
public class UserController {
	
	private UserService userService;
	private BundleService bundleService;
	private EmailSenderService emailSender;
	
	@Autowired
	public UserController(UserService theUserService, BundleService theBundleService, EmailSenderService theEmailSenderService) {
		userService = theUserService;
		bundleService = theBundleService;
		emailSender = theEmailSenderService;
	}
	
	@GetMapping("/users")
	public List<User> findAll(){
		
		List<User> users = userService.findAll();
		
		return users;
		
	}
	
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable int userId) {
		
		User theUser = userService.findById(userId);
		if(theUser == null) {
			throw new RuntimeException("User id not found - " + userId);
		}

		return theUser;
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User theUser) {
		theUser.setId(0);
		
		try {
			userService.save(theUser);
		} catch(Exception e) {
			if(e.getMessage().contains("constraint [user.usernameUnique]")){
				throw new RuntimeException("Username " + theUser.getUserName() +" already exists");
			} else {
				throw new RuntimeException(e.getMessage());
			}
		}
		
		emailSender.sendEmail(theUser.getEmail(),"insert subject", "http://localhost:8080/api/users/enable/" + theUser.getUserName());
		
		return theUser;
		
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User theUser) {
		
		try {
			userService.save(theUser);
		} catch(Exception e) {
			if(e.getMessage().contains("constraint [user.usernameUnique]")){
				throw new RuntimeException("Username " + theUser.getUserName() +" already exists.");
			} else {
				throw new RuntimeException(e.getMessage());
			}
		}

		return theUser;
	}
	
	
	@GetMapping("/users/enable/{username}")
	public User enableUser(@PathVariable String username) {
		User theUser = new User();
		try {
			theUser = userService.findByUserName(username);
		} catch(Exception e) {
			if(e.getMessage().contains("Index 0 out of bounds for length 0")) {
				//findByUserName stores the founded user in a list. If it doesn't find a user with this username, the list will be empty
				throw new RuntimeException("Username " + username + " not found.");
			}
			throw new RuntimeException(e.getMessage());
		}
		
		theUser.setEnabled(true);
		
		userService.save(theUser);
		
		return theUser;
		
	}
	
	@PutMapping("users/{userId}/addBundle/{bundleId}")
	public User addBundleToUser(@PathVariable int userId, @PathVariable int bundleId) {
		
		User theUser = userService.findById(userId);
		
		Bundle theBundle = bundleService.findById(bundleId);
		
		if(theUser.getBundles().contains(theBundle)) {
			throw new RuntimeException("User " + theUser.getUserName() + " already contains bundle " + theBundle.getName());
		}
		
		theUser.getBundles().add(theBundle);
		
		userService.save(theUser);
		
		return theUser;
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable int userId) {
		User theUser = userService.findById(userId);
		
		if(theUser == null) {
			throw new RuntimeException("User not found - " + userId);
		}
		
		userService.deleteById(userId);
		
		return "Deleted user id - " + userId;
	}
}
