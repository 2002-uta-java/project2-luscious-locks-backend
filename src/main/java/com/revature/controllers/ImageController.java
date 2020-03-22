package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Image;
import com.revature.models.User;
import com.revature.services.ImageService;
import com.revature.services.UserService;

@CrossOrigin
@RestController
public class ImageController {

	@Autowired
	private ImageService imageService;
	@Autowired
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(ImageController.class);

	@GetMapping("/images")
	public List<Image> getAllImages(@RequestParam(name = "rating", required = false) Float rating) {
		if (rating != null) {
			return imageService.getAtOrAboveRating(rating);
		} else {
			return imageService.getAll();
		}
	}

	@GetMapping("/images/{id}")
	public ResponseEntity<Image> getImage(@PathVariable("id") int id) {

		Image i = imageService.getById(id);
		logger.info("{}", i);
		if (i == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(i, HttpStatus.OK);
	}

	@GetMapping("/users/{id}/images")
	public ResponseEntity<List<Image>> getImagesForUser(@PathVariable("id") int id) {
		User poster = userService.getById(id);
		if (poster == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(imageService.getForUser(poster), HttpStatus.OK);
	}

	@PostMapping("/images")
	public ResponseEntity<String> createImage(@RequestBody Image i, HttpSession session) {
		User user = (User) session.getAttribute("user");
		logger.info("current user = {}", user);
		i.setPoster(user);
		boolean result = imageService.createImage(i);
		logger.info("createImage returned {}", result);
		if (result) {
			return new ResponseEntity<>("{\"message\": \"Added image " + i.getId() + "\"}", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Could not add image", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/images/{id}")
	public ResponseEntity<Image> updateImage(@RequestBody Image i, @PathVariable("id")int id) {
		if (i.getId() > 0 && i.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		i.setId(id);
		return new ResponseEntity<>(imageService.updateImage(i), HttpStatus.OK);
	
	}

	@DeleteMapping("/images/{id}")
	public ResponseEntity<String> deleteImage(@PathVariable("id")int id) {
		Image i = imageService.getById(id);
		if(i == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		boolean result = imageService.deleteImage(i);
		logger.debug("deleteImage returned {}", result);
		if (result) {
			return new ResponseEntity<>("Deleted image " + i.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Could not delete image", HttpStatus.BAD_REQUEST);
		}
	}
}
