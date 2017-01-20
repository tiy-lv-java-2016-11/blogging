package com.theironyard.repositories;

import com.theironyard.enitites.Comment;
import com.theironyard.enitites.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByEntry(Entry entry);
    Comment findByContent(String content);
}
