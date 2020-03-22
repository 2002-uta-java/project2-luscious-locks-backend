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
import com.revature.models.User;
import com.revature.services.CommentService;
import com.revature.services.UserService;
import com.revature.test.config.TestBeanConfig;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.*;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBeanConfig.class})
public class CommentControllerTest {
	
	@Autowired
	private CommentController commentController;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;

	@Test
	public void createCommentTest() {
		HttpSession session = new MockHttpSession();
		User u = userService.getById(1);
		session.setAttribute("user", u);
		Comment c = new Comment();
		c.setText("comment");
		ResponseEntity<String> result = commentController.createComment(c, 1, session);
		assertNotNull(result);
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		
		result = commentController.createComment(new Comment(), 1, session);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}
	@Test
	public void getCommentTest() {
		Comment c = new Comment();
		c.setText("cats");
		assertTrue(commentService.createComment(c));
		assertEquals(c, commentController.getComment(c.getId()).getBody());
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
		@GetMapping("/images/{id}/comments")
		public ResponseEntity<List<Comment>> getCommentsForImage(@PathVariable("id") int id) {
		--@PostMapping("/images/{id}/comments")
		--public ResponseEntity<String> createComment(@RequestBody Comment c, @PathVariable("id") int id,
		@PutMapping("/comments/{id}")
		public ResponseEntity<Comment> updateComment(@RequestBody Comment c,
		@DeleteMapping("/comments/{id}")
		public ResponseEntity<String> deleteComment(@PathVariable("id") int id) {
	*/

	
}