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
import com.revature.test.config.TestBeanConfig;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class UserControllerTest {
	
	@Autowired
	private UserController userController;

	@Test
	public void getUserTest() {
		ResponseEntity<User> result =  userController.getUser("1");
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	@Test
	public void createUserTest() {
		User u = new User(0, "u_745", "x", null,null,null,null);
		ResponseEntity<String> result = userController.createUser(u);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertNotEquals(0, u.getId());
	}
	///////////
	/*
		@GetMapping("/users")
		public ResponseEntity<List<User>> getAllUsers() {
		@GetMapping("/users/{idOrUsername}")
		public ResponseEntity<User> getUser(@PathVariable("idOrUsername") String idOrUsername) {

		@PostMapping("/login")
		public ResponseEntity<User> login(@RequestBody User creds) {
		@PostMapping("/users")
		public ResponseEntity<String> createUser(@RequestBody User u) {

		@PutMapping("/users/{id}")
		public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User u) {
*/
}