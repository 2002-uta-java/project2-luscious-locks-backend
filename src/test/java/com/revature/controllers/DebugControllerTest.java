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
import com.revature.models.Rating;
import com.revature.models.User;
import com.revature.services.ImageService;
import com.revature.services.RatingService;
import com.revature.services.UserService;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class DebugControllerTest {
	
	@Autowired
	private DebugController debugController;
	@Autowired
	private ImageController imageController;
	@Autowired
	private RatingController ratingController;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private RatingService ratingService;

	@Test
	public void populateTest() {
		HttpSession session = new MockHttpSession();
		ResponseEntity<String> result = debugController.populate();
		assertEquals("done", result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
	}
}