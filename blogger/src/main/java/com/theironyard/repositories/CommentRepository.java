package com.theironyard.repositories;

import com.theironyard.entities.Comment;
import com.theironyard.entities.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created by sparatan117 on 1/11/17.
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByEntry(Entry entry);
}
