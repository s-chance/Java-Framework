package com.entropy.springboot02jpa.dao;

import com.entropy.springboot02jpa.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CommentMapper extends JpaRepository<Comment, Integer> {
}
