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

import com.revature.models.Rating;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class RatingControllerTest {
	
	@Autowired
	private RatingController ratingController;
	
	@Test
	public void createRatingTest() {
		HttpSession session = new MockHttpSession();
		
		Rating r = new Rating();
		r.setRating(3f);
		ResponseEntity<String> result = ratingController.createRating(r, 1, session);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		result = ratingController.createRating(new Rating(), 0, session);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
}