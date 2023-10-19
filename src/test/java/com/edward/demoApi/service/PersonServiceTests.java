package com.edward.demoApi.service;

import com.edward.demoApi.dao.IPersonDao;
import com.edward.demoApi.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PersonServiceTests {

    @Autowired
    IPersonService personService;
    @MockBean
    IPersonDao mockPersonDAO;



    @Test
    void addPerson_calls_PersonDao_insertPerson_with_Person_object(){
        Person person = new Person("Ed");
        personService.addPerson(person);
        verify(mockPersonDAO, atLeastOnce()).insertPerson(person);
    }

    @Test
    void getAllPeople_gets_a_list_of_people_from_PersonDao (){
        List<Person> people = new ArrayList<>();
        people.add(new Person(UUID.randomUUID(),"Ed"));
        people.add(new Person(UUID.randomUUID(),"Tom"));
        when(mockPersonDAO.selectAllPeople()).thenReturn(people);
        Assert.isInstanceOf(List.class, personService.getAllPeople());
        assertEquals(personService.getAllPeople(), people);
        verify(mockPersonDAO, atLeastOnce()).selectAllPeople();
    }

    @Test
    void getPersonById_gets_a_person_Optional_with_matching_id_from_PersonDao (){
        Person person = new Person(UUID.randomUUID(),"Ed" );
        when(mockPersonDAO.selectPersonById(person.getId())).thenReturn(person);
        Assert.isInstanceOf(Optional.class, personService.getPersonById(person.getId()));
        verify(mockPersonDAO, atLeastOnce()).selectPersonById(person.getId());
        assertTrue(personService.getPersonById(person.getId()).isPresent());
    }

    @Test
    void deletePerson_calls_PersonDao_deletePersonById_with_person(){
        Person person = new Person(UUID.randomUUID(),"Ed" );
        personService.deletePerson(person.getId());
        verify(mockPersonDAO, atLeastOnce()).deletePersonById((person.getId()));
    }

    @Test
    void updatePerson_calls_PersonDao_updatePerson_with_person(){
        Person person = new Person(UUID.randomUUID(),"Ed" );
        personService.updatePerson(person);
        verify(mockPersonDAO, atLeastOnce()).updatePerson((person));
    }
}
