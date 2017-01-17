package com.theironyard.Repositories;

import com.theironyard.Entities.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by darionmoore on 1/14/17.
 */
public interface EntryRepository extends JpaRepository<Entry, Integer> {
    List<Entry> findAll();
}
