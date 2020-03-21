package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Image;
import com.revature.models.Rating;
import com.revature.models.User;
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

	@GetMapping("/images/{id}/ratings")
	public ResponseEntity<List<Rating>> getRatingsForImage(@PathVariable("id") int id,
			@RequestParam(name = "all", required = false) Boolean all, HttpSession session) {
		Image i = imageService.getById(id);
		if (i == null) {
			return new ResponseEntity<List<Rating>>(HttpStatus.NOT_FOUND);
		}
		List<Rating> allRatings = ratingService.getAllForImage(i);

		if (all != null && all) {
			return new ResponseEntity<>(allRatings, HttpStatus.OK);
		}

		User user = (User) session.getAttribute("user");
		List<Rating> returnedRatings = new ArrayList<>();
		Rating averageRating = new Rating();
		Rating usersRating = new Rating();

		float total = 0;
		if (!allRatings.isEmpty()) {
			for (Rating r : allRatings) {
				total += r.getRating();
				if (r.getRater().getId() == user.getId()) {
					usersRating = r;
				}
			}
			averageRating.setRating(total / allRatings.size());
		}
		returnedRatings.add(averageRating);
		returnedRatings.add(usersRating);
		return new ResponseEntity<>(returnedRatings, HttpStatus.OK);
	}

	@PostMapping("/images/{id}/ratings")
	public ResponseEntity<String> createRating(@RequestBody Rating r, @PathVariable("id") int id,
			HttpSession session) {
		User rater = (User) session.getAttribute("user");
		Image i = imageService.getById(id);
		if (i == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		r.setRater(rater);
		r.setImage(i);
		boolean result = ratingService.createRating(r);
		if (result) {
			return new ResponseEntity<String>("Created rating " + r.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Could not create rating", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/ratings/{id}")
	public ResponseEntity<Rating> updateRating(@RequestBody Rating r, @PathVariable("id") int id) {
		if (r.getId() > 0 && r.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		r.setId(id);
		return new ResponseEntity<>(ratingService.updateRating(r), HttpStatus.OK);
	}
}
