package com.theironyard.Repositories;

import com.theironyard.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by darionmoore on 1/14/17.
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstByUserName(String userName);
}
