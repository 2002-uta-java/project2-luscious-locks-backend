package com.revature.daos;

import java.util.List;

import com.revature.models.Image;

public interface ImageDAO {

	public List<Image> getAll();
	public Image getById(int id);
	public boolean createImage(Image i);
	public Image updateImage(Image i);
	public boolean deleteImage(Image i);
}
