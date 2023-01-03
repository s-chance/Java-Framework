package com.entropy.springboot02jpa;

import com.entropy.springboot02jpa.dao.CommentMapper;
import com.entropy.springboot02jpa.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class Springboot02JpaApplicationTests {

	@Autowired
	private CommentMapper commentMapper;
	@Test
	void contextLoads() {
		Optional<Comment> optional = commentMapper.findById(1);
		Comment comment = optional.get();
		System.out.println(comment);
	}

}
