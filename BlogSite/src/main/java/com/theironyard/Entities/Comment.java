package com.theironyard.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by darionmoore on 1/11/17.
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();


    public Comment() {
    }

    public Comment(String comment){
        this.comment = comment;
    }

    public Comment(int id, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
