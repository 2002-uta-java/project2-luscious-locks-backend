package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.daos.RatingDAO;
import com.revature.daos.RatingDAOImpl;
import com.revature.models.Image;
import com.revature.models.Rating;

@Service
public class RatingService {

	private RatingDAO ratingDAO = new RatingDAOImpl();

	public List<Rating> getAllForImage(Image i) {
		return ratingDAO.getAllForImage(i);
	}

	public Rating getById(int id) {
		return ratingDAO.getById(id);
	}

	public boolean createRating(Rating c) {
		return ratingDAO.createRating(c);
	}

	public Rating updateRating(Rating c) {
		return ratingDAO.updateRating(c);
	}

	public boolean deleteRating(Rating c) {
		return ratingDAO.deleteRating(c);
	}
}
