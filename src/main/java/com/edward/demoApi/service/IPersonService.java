package com.edward.demoApi.service;

import com.edward.demoApi.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPersonService {
    int addPerson(Person person);
    List<Person> getAllPeople();
    Optional<Person> getPersonById(UUID id);
    int deletePerson(UUID id);
    int updatePerson(Person person);
}
