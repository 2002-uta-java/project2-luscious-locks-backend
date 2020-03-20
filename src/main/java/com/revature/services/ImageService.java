package com.revature.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.revature.daos.ImageDAO;
import com.revature.daos.ImageDAOImpl;
import com.revature.models.Image;
import com.revature.models.User;

@Service
public class ImageService {

	private ImageDAO imageDAO = new ImageDAOImpl();
	
	public List<Image> getAll(){
		return imageDAO.getAll();
	}
	
	public Image getById(int id) {
		return imageDAO.getById(id);
	}
	
	public boolean createImage(Image i) {
		return imageDAO.createImage(i);
	}
	
	public boolean deleteImage(Image i) {
		return imageDAO.deleteImage(i);
	}
	
	public Image updateImage(Image i) {
		return imageDAO.updateImage(i);
	}

	public List<Image> getForUser(User poster) {
		return imageDAO.getForUser(poster);
	}

	public List<Image> getAtOrAboveRating(float rating) {
		return imageDAO.getAtOrAvoceRating(rating);
	}
}
