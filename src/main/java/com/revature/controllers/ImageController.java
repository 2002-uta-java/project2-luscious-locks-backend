package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Image;
import com.revature.services.ImageService;

@RestController
public class ImageController {

	@Autowired
	private ImageService imageService;
	
	@GetMapping("/images")
	public List<Image> getAllImages(){
		return imageService.getAll();
	}
	
	@GetMapping("/images/{id}")
	public Image getImage(@PathVariable("id")int id) {
		return imageService.getById(id);
	}
	
	@PostMapping("/images")
	public ResponseEntity<String> createImage(@RequestBody Image i) {
		boolean result = imageService.createImage(i);
		//Debugging code
		System.out.println("createImage returned " + result);
		if(result) {
			return new ResponseEntity<>("Added image " +  i.getId(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Could not add image", HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/images")
	public Image updateImage(@RequestBody Image i) {
		return imageService.updateImage(i);
	}
	
	@DeleteMapping("/images")
	public ResponseEntity<String> deleteImage(@RequestBody Image i){
		boolean result = imageService.deleteImage(i);
		//Debugging code
		System.out.println("deleteImage returned " + result);
		if(result) {
			return new ResponseEntity<>("Deleted image " +  i.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Could not delete image", HttpStatus.BAD_REQUEST);
		}
	}
}
