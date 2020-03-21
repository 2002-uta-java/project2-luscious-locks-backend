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
	public void createUserTest() {
		ResponseEntity<User> result =  userController.getUser("1");
		assertNotNull(result);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
}