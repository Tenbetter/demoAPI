package com.edward.demoApi.service;

import com.edward.demoApi.dao.PersonDao;
import com.edward.demoApi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@service marks this as a service bean to be instantiated and injected into any class that needs it by spring
//@Component can be used also, but service is more descriptive
@Service
public class PersonService {

    private final PersonDao personDao;

    //@Autowired is used on constructors, setters and properties to inject dependencies
    //@Qualifier is used to specify which implementation of an interface to use
    @Autowired
    public PersonService(@Qualifier("FakePersonDAO") PersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person){
        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople(){
        return personDao.selectAllPeople();
    }

    public Optional<Person> getPersonById(UUID id){
        return personDao.selectPersonById(id);
    }

    public int deletePerson(UUID id){
        return personDao.deletePersonById(id);
    }

    public int updatePerson(Person person){
        return personDao.updatePerson(person);
    }
}
