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
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }

    @GetMapping(path ="{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return personService.getPersonById(id).orElse(null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deletePersonById(@PathVariable("id") UUID id){
        personService.deletePerson(id);
    }

    @PutMapping("/update")
    public void updatePerson(@RequestBody @Valid @NonNull Person person){
        personService.updatePerson(person);
    }
}
