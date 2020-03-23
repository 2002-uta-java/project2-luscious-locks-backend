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
import com.revature.services.UserService;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestBeanConfig.class })
public class RatingControllerTest {

	@Autowired
	private RatingController ratingController;
	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;

	@Test
	public void createRatingTest() {
		HttpSession session = new MockHttpSession();
		// User u = new User(0, "u_createRating", "x", null,null,null,null);
		// userService.createUser(u);
		// assertNotEquals(0, u.getId());
		Image i = new Image(0, "createRating", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());

		Rating r = new Rating();
		r.setRating(3f);
		ResponseEntity<String> result = ratingController.createRating(r, i.getId(), session);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		result = ratingController.createRating(new Rating(), 0, session);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	public void getRatingTest() {
		HttpSession session = new MockHttpSession();

		Image i = new Image(0, "getRatings", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());

		Rating r = new Rating();
		r.setRating(3f);
		ResponseEntity<String> result = ratingController.createRating(r, i.getId(), session);
		assertNotEquals(0, r.getId());

		ResponseEntity<Rating> result2 = ratingController.getRating(r.getId());
		assertEquals(HttpStatus.OK, result2.getStatusCode());

		result2 = ratingController.getRating(0);
		assertEquals(HttpStatus.NOT_FOUND, result2.getStatusCode());
	}

	@Test
	public void getRatingsForImageTest() {
		HttpSession session = new MockHttpSession();
		User u = new User(0, "u_getRatingForImage", "x", null,null,null,null);
		assertTrue(userService.createUser(u));
		session.setAttribute("user", u);
		
		Image i = new Image(0, "getRatingsForImage", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());
		
		Rating r = new Rating();
		r.setRating(3f);
		ResponseEntity<String> result = ratingController.createRating(r, i.getId(), session);
		assertNotEquals(0, r.getId());
		
		ResponseEntity<List<Rating>> result2 = ratingController.getRatingsForImage(i.getId(), null, session);
		assertEquals(HttpStatus.OK, result2.getStatusCode());
		
		result2 = ratingController.getRatingsForImage(i.getId(), true, session);
		assertEquals(HttpStatus.OK, result2.getStatusCode());
	}
	
	@Test
	public void updateRatingTest() {
		HttpSession session = new MockHttpSession();

		Image i = new Image(0, "updateRating", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());

		Rating r = new Rating();
		r.setRating(3f);
		ResponseEntity<String> result = ratingController.createRating(r, i.getId(), session);
		assertNotEquals(0, r.getId());

		r.setRating(5f);
		ResponseEntity<Rating> result2 = ratingController.updateRating(r, r.getId());
		assertEquals(HttpStatus.OK, result2.getStatusCode());
		assertEquals(r, result2.getBody());
	}
	/////////////
	/*
	 * --@GetMapping("/ratings/{id}") --public ResponseEntity<Rating>
	 * getRating(@PathVariable("id") int id) {
	 * 
	 * @GetMapping("/images/{id}/ratings") public ResponseEntity<List<Rating>>
	 * getRatingsForImage(@PathVariable("id") int id,
	 * 
	 * @RequestParam(name = "all", required = false) Boolean all, HttpSession
	 * session) {
	 * 
	 * --@PostMapping("/images/{id}/ratings") --public ResponseEntity<String>
	 * createRating(@RequestBody Rating r, @PathVariable("id") int id, --
	 * HttpSession session) {
	 * 
	 * @PutMapping("/ratings/{id}") public ResponseEntity<Rating>
	 * updateRating(@RequestBody Rating r, @PathVariable("id") int id) {
	 */
}