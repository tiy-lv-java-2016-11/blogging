package com.theironyard.Repositories;

import com.theironyard.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by darionmoore on 1/14/17.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAll();
}
