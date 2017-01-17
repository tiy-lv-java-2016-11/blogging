package com.theironyard.Entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by darionmoore on 1/11/17.
 */
@Entity
@Table(name = "entries")
public class Entry {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String entryTitle;

    @Column(nullable = false)
    private String entryDescription;

    @Column(nullable = false)
    private String entry;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    private User user;

    public Entry() {
    }

    public Entry(String entryTitle, String entryDescription, String entry, User user) {
        this.entryTitle = entryTitle;
        this.entryDescription = entryDescription;
        this.entry = entry;
        this.user = user;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
