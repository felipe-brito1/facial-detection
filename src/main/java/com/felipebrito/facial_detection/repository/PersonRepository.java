package com.felipebrito.facial_detection.repository;

import com.felipebrito.facial_detection.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
