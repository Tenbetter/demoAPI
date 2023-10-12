package com.edward.demoApi.controller;

import com.edward.demoApi.model.Person;
import com.edward.demoApi.service.IPersonService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@RestController annotation marks this out as a bean for creation by spring
//@RequestMapping denotes the controller level path
@RestController
@RequestMapping("/person")
public class PersonController {
    Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final IPersonService personService;

    //@Autowired annotation provides the spring instantiated PersonService to the constructor, This is dependency injection.
    //In this instance @Qualifier is not required because there is only one PersonService
    @Autowired
    public PersonController(IPersonService personService) {
        this.personService = personService;
    }

    //@PostMapping annotation marks this out as a method that accepts post requests
    //@Valid enforces validation that is defined in the model class
    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody @Valid @NonNull Person person){
        try{
            personService.addPerson(person);
        }catch (Error e){
            logger.info(e.toString());
            return new ResponseEntity(HttpStatus.SERVICE_UNAVAILABLE);
        }
         return new ResponseEntity(HttpStatus.CREATED);
    }
    //Adding path to the annotation appends to the controller level path
    @GetMapping("/all")
    public ResponseEntity <List<Person>> getAllPeople(){
        return ResponseEntity.ok(personService.getAllPeople());
    }

    @GetMapping(path ="{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") UUID id){
        Optional<Person> personOptional = personService.getPersonById(id);
//        if(personOptional.isPresent()){
//            return ResponseEntity.of(personOptional);
//        }

        return ResponseEntity.of(personOptional);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deletePersonById(@PathVariable("id") UUID id){
        try {
            personService.deletePerson(id);
            return new ResponseEntity("Deleted", HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updatePerson(@RequestBody @Valid @NonNull Person person){
        try {
            personService.updatePerson(person);
            return new ResponseEntity("Updated", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
