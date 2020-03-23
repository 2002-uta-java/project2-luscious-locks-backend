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

import com.revature.models.Comment;
import com.revature.models.Image;
import com.revature.models.Rating;
import com.revature.models.User;
import com.revature.services.CommentService;
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
public class ImageControllerTest {
	
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
	@Autowired
	private CommentService commentService;

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
	@Test
	public void deleteImageTest() {
		Image i = new Image(0, "url", null, "description", null, null);
		assertTrue(imageService.createImage(i));
		int id = i.getId();
		assertTrue(commentService.createComment(new Comment(0, "deleteImage", null,null,i)));
		ResponseEntity<String> result = imageController.deleteImage(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNull(imageService.getById(id));
	}
	
	@Test
	public void getAllImagesTest() {
		HttpSession session = new MockHttpSession();
		Image i = new Image(0, "getAllImages", null, "description", null, null);
		assertTrue(imageService.createImage(i));
		assertTrue(imageService.createImage(new Image(0, "getAllImage 2", null, "desc", null, null)));
		assertTrue(i.getId() > 0);
		Rating r = new Rating(0, 4.5f, null, i);
		
		assertTrue(ratingService.createRating(r));
		List<Image> result = imageController.getAllImages(null);
		int allCount = result.size();
		assertTrue(result.size() >= 1);
		result = imageController.getAllImages(4f);
		int highlyRatedCount = result.size();
		assertTrue(result.size() >= 1);
		assertTrue(allCount > highlyRatedCount);
		result = imageController.getAllImages(9000f);
		assertTrue(result.isEmpty());
	}
	
	@Test
	public void getImageTest() {
		HttpSession session = new MockHttpSession();
		Image i = new Image(0, "getImage", null, "description", null, null);
		assertTrue(imageService.createImage(i));
		assertTrue(i.getId() > 0);
		
		ResponseEntity<Image> result = imageController.getImage(i.getId());
		assertEquals(i, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void getImagesForUserTest() {
		HttpSession session = new MockHttpSession();
		User u = new User(0, "u_getImagesForUser", "x", null,null,null,null);
		assertTrue(userService.createUser(u));
		Image i = new Image(0, "getImageForUser", null, "description", u, null);
		assertTrue(imageService.createImage(i));
		assertTrue(i.getId() > 0);
		
		ResponseEntity<List<Image>> result = imageController.getImagesForUser(u.getId());
		assertTrue(result.getBody().size() >= 1);
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	@Test
	public void updateImageTest() {
		HttpSession session = new MockHttpSession();
		Image i = new Image(0, "updateImage", null, "description", null, null);
		assertTrue(imageService.createImage(i));
		assertTrue(i.getId() > 0);
		
		i.setAccepted(true);
		ResponseEntity<Image> result = imageController.updateImage(i, i.getId());
		assertEquals(i, result.getBody());
		assertEquals(HttpStatus.OK, result.getStatusCode());
	}
	
	/////////////
/*
	--@GetMapping("/images")
	--public List<Image> getAllImages(@RequestParam(name = "rating", required = false) Float rating) {
	@GetMapping("/images/{id}")
	public ResponseEntity<Image> getImage(@PathVariable("id") int id) {
	@GetMapping("/users/{id}/images")
	public ResponseEntity<List<Image>> getImagesForUser(@PathVariable("id") int id) {
	--@PostMapping("/images")
	--public ResponseEntity<String> createImage(@RequestBody Image i, HttpSession session) {
	@PutMapping("/images/{id}")
	public ResponseEntity<Image> updateImage(@RequestBody Image i, @PathVariable("id")int id) {
	--@DeleteMapping("/images")
	--public ResponseEntity<String> deleteImage(@RequestBody Image i) {
*/
}