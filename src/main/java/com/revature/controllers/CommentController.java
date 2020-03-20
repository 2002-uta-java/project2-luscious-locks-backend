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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.Comment;
import com.revature.services.CommentService;

@CrossOrigin
@RestController
public class CommentController {
	@Autowired
	private CommentService commentService;
	Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@GetMapping("/comments/{id}")
	public ResponseEntity<Comment> getComment(@PathVariable("id")int id) {
		Comment c;
		c = commentService.getById(id);
		if(c == null) {
			return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Comment>(c, HttpStatus.OK);
	}
}
