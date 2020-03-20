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
		logger.info("" + i);
		if (i == null) {
			return new ResponseEntity<Image>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Image>(i, HttpStatus.OK);
	}

	@GetMapping("/users/{id}/images")
	public ResponseEntity<List<Image>> getImagesForUser(@PathVariable("id") int id) {
		User poster = userService.getById(id);
		if (poster == null) {
			return new ResponseEntity<List<Image>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Image>>(imageService.getForUser(poster), HttpStatus.OK);
	}

	@PostMapping("/images")
	public ResponseEntity<String> createImage(@RequestBody Image i, HttpSession session) {
		User user = (User) session.getAttribute("user");
		logger.info("current user = " + user);
		i.setPoster(user);
		boolean result = imageService.createImage(i);
		logger.info("createImage returned " + result);
		if (result) {
			return new ResponseEntity<>("Added image " + i.getId(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Could not add image", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/images")
	public Image updateImage(@RequestBody Image i) {
		return imageService.updateImage(i);
	}

	@DeleteMapping("/images")
	public ResponseEntity<String> deleteImage(@RequestBody Image i) {
		boolean result = imageService.deleteImage(i);
		// Debugging code
		System.out.println("deleteImage returned " + result);
		if (result) {
			return new ResponseEntity<>("Deleted image " + i.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Could not delete image", HttpStatus.BAD_REQUEST);
		}
	}
}
