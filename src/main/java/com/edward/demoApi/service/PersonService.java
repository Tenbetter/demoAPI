package com.edward.demoApi.service;

import com.edward.demoApi.dao.IPersonDao;
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
public class PersonService implements IPersonService{

    private final IPersonDao personDao;

    //@Autowired is used on constructors, setters and properties to inject dependencies
    //@Qualifier is used to specify which implementation of an interface to use when there is ambiguity
    @Autowired
    public PersonService(@Qualifier("sqlServer") IPersonDao personDao) {
        this.personDao = personDao;
    }

    public int addPerson(Person person){
        return personDao.insertPerson(person);
    }

    public List<Person> getAllPeople(){
        return personDao.selectAllPeople();
    }

    public Optional<Person> getPersonById(UUID id){
        if(personDao.selectPersonById(id).isPresent()) {
            return personDao.selectPersonById(id);
        }else{
            return Optional.empty();
        }
    }

    public void deletePerson(UUID id){
        if(personDao.selectPersonById(id).isPresent()){
            personDao.deletePersonById(id);
        }//else throw not found exception?
    }

    public void updatePerson(Person person){
        if(personDao.selectPersonById(person.getId()).isPresent()){
            personDao.updatePerson(person);
        }//else throw not found exception?

    }
}
