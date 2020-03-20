package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Image;
import com.revature.models.Rating;
import com.revature.services.ImageService;
import com.revature.services.RatingService;

@CrossOrigin
@RestController
public class RatingController {
	@Autowired
	private RatingService ratingService;
	@Autowired
	private ImageService imageService;

	Logger logger = LoggerFactory.getLogger(RatingController.class);

	@GetMapping("/ratings/{id}")
	public ResponseEntity<Rating> getRating(@PathVariable("id") int id) {
		Rating r;
		r = ratingService.getById(id);
		if (r == null) {
			return new ResponseEntity<Rating>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Rating>(r, HttpStatus.OK);

	}
	@GetMapping("/ratings/images/{id}")
	public ResponseEntity<List<Rating>> getRatingsForImage(@PathVariable("id") int id) {
		Image i = imageService.getById(id);
		return new ResponseEntity<>(ratingService.getAllForImage(i), HttpStatus.OK);
	}
}
