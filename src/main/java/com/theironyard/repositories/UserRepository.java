package com.theironyard.repositories;

import com.theironyard.enitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByUsername(String username);
}
