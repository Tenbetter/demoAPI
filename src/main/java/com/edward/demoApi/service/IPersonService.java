package com.edward.demoApi.service;

import com.edward.demoApi.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPersonService {
    void addPerson(Person person);
    List<Person> getAllPeople();
    Optional<Person> getPersonById(UUID id);
    void deletePerson(UUID id);
    void updatePerson(Person person);
}
