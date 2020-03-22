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
import com.revature.services.ImageService;
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
	@Autowired
	private ImageService imageService;

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
		ResponseEntity<String> result = imageController.deleteImage(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNull(imageService.getById(id));
	}
	/////////////
/*
	@GetMapping("/images")
	public List<Image> getAllImages(@RequestParam(name = "rating", required = false) Float rating) {
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