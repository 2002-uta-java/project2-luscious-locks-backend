package com.revature.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.test.config.TestBeanConfig;

import static org.junit.Assert.*;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBeanConfig.class })
public class UserControllerTest {

	@Autowired
	private UserController userController;
	@Autowired
	private UserService userService;

	@Test
	public void getAllUsersTest() {
		User u = new User(0, "u_getAllUsersTest", "x", null, null, null, null);
		assertTrue(userService.createUser(u));

		ResponseEntity<List<User>> result = userController.getAllUsers();
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void getUserTest() {
		User u = new User(0, "u_getUserTest", "x", null, null, null, null);
		assertTrue(userService.createUser(u));

		ResponseEntity<User> result = userController.getUser(new Integer(u.getId()).toString());
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void loginTest() {
		User u = new User(0, "u_loginTest", "x", null, null, null, null);
		assertTrue(userService.createUser(u));

		ResponseEntity<User> result = userController.login(u);
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	public void createUserTest() {
		User u = new User(0, "u_createUserTest", "x", null, null, null, null);
		ResponseEntity<String> result = userController.createUser(u);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertNotEquals(0, u.getId());
	}
	
	@Test
	public void updateUserTest() {
		User u = new User(0, "u_updateUserTest", "x", null, null, null, null);
		assertTrue(userService.createUser(u));
		u.setBanned(true);
		u.setPassword(null);
		ResponseEntity<User> result = userController.updateUser(u.getId(), u);
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(u, result.getBody());
	}
	///////////
	/*
	 * --@GetMapping("/users") 
	 * --public ResponseEntity<List<User>> getAllUsers() {
	 * --@GetMapping("/users/{idOrUsername}") 
	 * --public ResponseEntity<User> getUser(@PathVariable("idOrUsername") String idOrUsername) {
	 * 
	 * @PostMapping("/login") public ResponseEntity<User> login(@RequestBody User
	 * creds) { --@PostMapping("/users") --public ResponseEntity<String>
	 * createUser(@RequestBody User u) {
	 * 
	 * @PutMapping("/users/{id}") public ResponseEntity<User>
	 * updateUser(@PathVariable("id") int id, @RequestBody User u) {
	 */
}