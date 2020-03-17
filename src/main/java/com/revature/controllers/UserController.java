package com.revature.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;


@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAll();
	}
	
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable("id")int id) {
		return userService.getById(id);
	}
	
	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody User u) {
		boolean result = userService.createUser(u);
		System.out.println("createUser returned " + result);
		if(result) {
			return new ResponseEntity<>("Added user " +  u.getUsername(), HttpStatus.CREATED);
		} else {
			// TODO is this the best status code to use for failure?
			return new ResponseEntity<>("Could not add user", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/users")
	public User updateUser(@RequestBody User u) {
		return userService.updateUser(u);
	}
}
