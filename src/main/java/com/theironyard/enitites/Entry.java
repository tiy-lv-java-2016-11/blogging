package com.theironyard.enitites;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entries")
public class Entry {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false)
    private String title;
    @Column
    private String snippet;
    @Column(nullable = false)
    private String post;
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
    private LocalDateTime lastModified;
    @ManyToOne
    private User user;

    public Entry() {
    }

    public Entry(String title, String snippet, String post, User user) {
        this.title = title;
        this.snippet = snippet;
        this.post = post;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
