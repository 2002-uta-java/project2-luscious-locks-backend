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
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Comment;
import com.revature.models.Image;
import com.revature.models.User;
import com.revature.services.CommentService;
import com.revature.services.ImageService;

@CrossOrigin
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private ImageService imageService;

	Logger logger = LoggerFactory.getLogger(CommentController.class);

	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getComment(@PathVariable("id") int id) {
		Comment c;
		c = commentService.getById(id);
		if (c == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(c, HttpStatus.OK);
	}

	@GetMapping("/images/{id}/comments")
	public ResponseEntity<List<Comment>> getCommentsForImage(@PathVariable("id") int id) {
		Image i = imageService.getById(id);
		if (i == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(commentService.getAllForImage(i), HttpStatus.OK);
	}

	@PostMapping("/images/{id}/comments")
	public ResponseEntity<String> createComment(@RequestBody Comment c, @PathVariable("id") int id,
			HttpSession session) {
		User author = (User) session.getAttribute("user");
		Image i = imageService.getById(id);
		if (i == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		c.setAuthor(author);
		c.setImage(i);
		boolean result = commentService.createComment(c);
		if (result) {
			return new ResponseEntity<>("Created comment " + c.getId(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Could not create comment", HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/comments/{id}")
	public ResponseEntity<Comment> updateComment(@RequestBody Comment c,
			@PathVariable("id") int id) {
		if (c.getId() > 0 && c.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		c.setId(id);
		return new ResponseEntity<>(commentService.updateComment(c), HttpStatus.OK);
	}

	@DeleteMapping("/comments/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable("id") int id) {
		Comment c = commentService.getById(id);
		if (c == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		boolean result = commentService.deleteComment(c);
		if (result) {
			return new ResponseEntity<>("Delete comment " + c.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Could not delete comment", HttpStatus.BAD_REQUEST);
		}
	}
}
