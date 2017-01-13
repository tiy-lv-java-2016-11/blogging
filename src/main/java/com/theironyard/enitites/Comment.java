package com.theironyard.enitites;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
    private LocalDateTime lastModified;
    @ManyToOne
    private Entry entry;
    @ManyToOne
    private User user;

    public Comment() {
    }

    public Comment(String content, Entry entry, User user) {
        this.content = content;
        this.entry = entry;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
