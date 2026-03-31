package com.felipebrito.facial_detection.controller;

import com.felipebrito.facial_detection.models.Person;
import com.felipebrito.facial_detection.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @Operation(summary = "Cadastra uma nova pessoa.")
    @PostMapping("/")
    ResponseEntity<Person> create(@RequestBody Person person){
        Person saved = personService.save(person);
        return ResponseEntity.ok(saved);

    }
    @Operation(summary = "Captura 30 fotos do rosto via webcam para treino.")
    @PostMapping("/{id}/capture")
    ResponseEntity<String> capturePhotos(@PathVariable Long id)  {
        personService.capturePhotos(id);
        return ResponseEntity.ok("Photos captured successfully.");
    }
}
