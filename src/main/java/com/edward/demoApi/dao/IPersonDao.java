package com.edward.demoApi.dao;

import com.edward.demoApi.model.Person;

import java.util.List;
import java.util.UUID;

public interface IPersonDao {

    void insertPerson (Person person);

    public List<Person> selectAllPeople();

    void deletePersonById(UUID id);

    void updatePerson(Person person);

    Person selectPersonById(UUID id);
}
