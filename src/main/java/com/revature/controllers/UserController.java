package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.User;
import com.revature.services.UserService;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
	}

	@GetMapping("/users/{idOrUsername}")
	public ResponseEntity<User> getUser(@PathVariable("idOrUsername") String idOrUsername) {
		User u;
		if (idOrUsername.matches("^\\d+$")) {
			u = userService.getById(Integer.valueOf(idOrUsername));
		} else {
			u = userService.getByUsername(idOrUsername);
		}
		if (u == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User creds) {
		String username = creds.getUsername();
		String password = creds.getActualPassword();
		logger.info("controller got username/pw: {} {}", username, password);
		User u = userService.getByUsernameAndPassword(username, password);
		if (u == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(u, HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<String> createUser(@RequestBody User u) {
		boolean result = userService.createUser(u);
		logger.info("createUser returned {}", result);
		if (result) {
			return new ResponseEntity<>("{\"message\": \"Added user " + u.getId() + "\"}", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Could not add user", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User u) {
		logger.info("u.getId() = {} id = {}", u.getId(), id);
		if (u.getId() > 0 && u.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		u.setId(id);
		return new ResponseEntity<>(userService.updateUser(u), HttpStatus.OK);
	}
}
