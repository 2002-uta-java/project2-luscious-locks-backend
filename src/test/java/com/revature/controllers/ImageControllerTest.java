package com.revature.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.models.Image;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class ImageControllerTest {
	
	@Autowired
	private ImageController imageController;
	@Autowired
	private UserService userService;

	@Test
	public void createImageTest() {
		
		HttpSession session = new MockHttpSession();
		User u = userService.getById(1);
		session.setAttribute("user", u);
		Image i = new Image();
		i.setUrl("url");
		i.setDescription("desc");
		ResponseEntity<String> result = imageController.createImage(i, session);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		result = imageController.createImage(new Image(), session);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
}