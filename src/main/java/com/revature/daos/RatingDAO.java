package com.revature.daos;

import java.util.List;

import com.revature.models.Image;
import com.revature.models.Rating;

public interface RatingDAO {
	public List<Rating> getAllForImage(Image i);
	public Rating getById(int id);
	public boolean createRating(Rating r);
	public boolean deleteRating(Rating r);
	public Rating updateRating(Rating r);
}
