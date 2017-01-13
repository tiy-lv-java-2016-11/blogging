package com.theironyard.enitites;

import com.theironyard.Privileges;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private int id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private Enum privilege;
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column
    private LocalDateTime lastModified;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.privilege = Privileges.SUBSCRIBER;
    }

    public User(String username, String password, Enum privilege) {
        this.username = username;
        this.password = password;
        this.privilege = privilege;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Enum getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Enum privilege) {
        this.privilege = privilege;
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
}
