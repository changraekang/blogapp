package com.cos.blogapp.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	
	
	@Query(value = "select * from comment inner JOIN board on comment.boardid = board.id where board.id = :id", nativeQuery = true)
	List<Comment> getComment( int id);
}	
