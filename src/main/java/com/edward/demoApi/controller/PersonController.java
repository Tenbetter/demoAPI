package com.edward.demoApi.controller;

import com.edward.demoApi.model.Person;
import com.edward.demoApi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController annotation marks this out as a bean for creation by spring
@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    //@Autowired annotation provides the spring instantiated PersonService to the constructor, This is dependency injection.
    //In this instance @Qualifier is not required because there is only one PersonService and no interface
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    //@PostMapping annotation marks this out as a method that accepts post requests
    @PostMapping
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }

    @RequestMapping("/all")
    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }
}
