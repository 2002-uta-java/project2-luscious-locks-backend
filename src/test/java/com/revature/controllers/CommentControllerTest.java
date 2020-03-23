package com.revature.controllers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.revature.models.Comment;
import com.revature.models.Image;
import com.revature.services.CommentService;
import com.revature.services.ImageService;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

import java.util.List;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class CommentControllerTest {
	
	@Autowired
	private CommentController commentController;
	@Autowired
	private CommentService commentService;
	@Autowired
	private ImageService imageService;

	@Test
	public void createCommentTest() {
		HttpSession session = new MockHttpSession();
		
		Image i = new Image(0, "url", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());
		
		Comment c = new Comment();
		c.setText("comment");
		ResponseEntity<String> result = commentController.createComment(c, i.getId(), session);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		
		result = commentController.createComment(new Comment(), i.getId(), session);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	@Test
	public void updateCommentTest() {
		HttpSession session = new MockHttpSession();
		
		Image i = new Image(0, "url", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());
		
		Comment c = new Comment();
		c.setText("comment");
		ResponseEntity<String> result = commentController.createComment(c, i.getId(), session);
		assertNotNull(result);
		
		c.setText("new text");
		ResponseEntity<Comment> result2 = commentController.updateComment(c, c.getId()+1);
		assertEquals(HttpStatus.BAD_REQUEST, result2.getStatusCode());
		
		result2 = commentController.updateComment(c, c.getId());
		assertEquals(HttpStatus.OK, result2.getStatusCode());
	}
	@Test
	public void getCommentTest() {
		Comment c = new Comment();
		c.setText("cats");
		assertTrue(commentService.createComment(c));
		assertEquals(c, commentController.getComment(c.getId()).getBody());
	}
	@Test
	public void getCommentForImageTest() {
		HttpSession session = new MockHttpSession();
		
		Image i = new Image(0, "url", null, "desc", null, null);
		imageService.createImage(i);
		assertNotEquals(0, i.getId());
		
		Comment c = new Comment();
		c.setText("comment");
		ResponseEntity<String> result = commentController.createComment(c, c.getId(), session);
		assertNotNull(result);
		
		ResponseEntity<List<Comment>> result2 = commentController.getCommentsForImage(i.getId());
		assertEquals(HttpStatus.OK, result2.getStatusCode());
		
		result2 = commentController.getCommentsForImage(0);
		assertEquals(HttpStatus.NOT_FOUND, result2.getStatusCode());
	}
	@Test
	public void deleteCommentTest() {
		Comment c = new Comment(0, "cat", null, null, null);
		assertTrue(commentService.createComment(c));
		int id = c.getId();
		ResponseEntity<String> result = commentController.deleteComment(id);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertNull(commentService.getById(id));
	}
	/////////////////
	/*
		--@GetMapping("/comments/{id}")
		--public ResponseEntity<Comment> getComment(@PathVariable("id") int id) {
		--@GetMapping("/images/{id}/comments")
		--public ResponseEntity<List<Comment>> getCommentsForImage(@PathVariable("id") int id) {
		--@PostMapping("/images/{id}/comments")
		--public ResponseEntity<String> createComment(@RequestBody Comment c, @PathVariable("id") int id,
		--@PutMapping("/comments/{id}")
		--public ResponseEntity<Comment> updateComment(@RequestBody Comment c,
		--@DeleteMapping("/comments/{id}")
		--public ResponseEntity<String> deleteComment(@PathVariable("id") int id) {
	*/

	
}