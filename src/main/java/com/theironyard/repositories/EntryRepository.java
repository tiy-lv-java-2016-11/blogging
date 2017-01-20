package com.theironyard.repositories;

import com.theironyard.enitites.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Integer> {
    Entry findByTitle(String title);
}
