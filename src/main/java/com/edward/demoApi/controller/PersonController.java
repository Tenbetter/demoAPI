package com.edward.demoApi.controller;

import com.edward.demoApi.model.Person;
import com.edward.demoApi.service.IPersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

//@RestController annotation marks this out as a bean for creation by spring
//@RequestMapping denotes the controller level path
@RestController
@RequestMapping("/person")
public class PersonController {
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
    public void addPerson(@RequestBody @Valid @NonNull Person person){
        personService.addPerson(person);
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
