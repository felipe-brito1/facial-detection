package com.felipebrito.facial_detection.controller;

import com.felipebrito.facial_detection.models.Person;
import com.felipebrito.facial_detection.services.PersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @PostMapping("/")
    ResponseEntity<Person> create(@RequestBody Person person){
        Person saved = personService.save(person);
        return ResponseEntity.ok(saved);

    }
}
