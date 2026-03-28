package com.felipebrito.facial_detection.services;

import com.felipebrito.facial_detection.models.Person;
import com.felipebrito.facial_detection.repository.PersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }

    public Person save(Person person){
        return personRepository.save(person);
    }
}
