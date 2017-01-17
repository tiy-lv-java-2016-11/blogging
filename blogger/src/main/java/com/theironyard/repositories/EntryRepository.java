package com.theironyard.repositories;

import com.theironyard.entities.Entry;
import com.theironyard.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by sparatan117 on 1/11/17.
 */
public interface EntryRepository extends JpaRepository<Entry, Integer> {
    List<Entry> findByUser(User user);
}
