package com.edward.demoApi;

import com.edward.demoApi.controller.PersonController;
import com.edward.demoApi.model.Person;
import com.edward.demoApi.service.IPersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    IPersonService mockPersonservice;

    @Test
    void addPerson_returns_ResponseEntity(){
        //Given
        PersonController personController = new PersonController(mockPersonservice);
        //When
        when(mockPersonservice.addPerson(any())).thenReturn(1);
        //Then
        Assert.isInstanceOf(ResponseEntity.class, personController.addPerson(new Person(UUID.randomUUID(),"Ed")));
        assertThat(personController.addPerson(new Person(UUID.randomUUID(),"Ed")).getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    @Test
    void addPerson_returns_ResponseEntity_with_status_400_when_request_is_bad(){

    }

    @Test
    void addPerson_returns_ResponseEntity_with_status_500_when_DB_connection_is_broken(){

    }
    @Test
    void updatePerson_returns_ResponseEntity(){
        PersonController personController = new PersonController(mockPersonservice);
        Assert.isInstanceOf(ResponseEntity.class,personController.updatePerson(new Person(UUID.randomUUID(),"ED")) );
        assertThat(personController.updatePerson(new Person(UUID.randomUUID(),"ED")).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAllPeople_returns_ResponseEntity(){
        //Given
        PersonController personController = new PersonController(mockPersonservice);
        List<Person> returnedList = new ArrayList<>();
        returnedList.add(new Person(UUID.randomUUID(), "ED"));
        //When
        when(mockPersonservice.getAllPeople()).thenReturn(returnedList);
        //Then
        Assert.isInstanceOf(ResponseEntity.class, personController.getAllPeople());
        assertThat(personController.getAllPeople().getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    @Test
    void getPersonById_returns_ResponseEntity(){
        //Given
        PersonController personController = new PersonController(mockPersonservice);
        Person person = new Person(UUID.randomUUID(),"ED");
        //When
        when(mockPersonservice.getPersonById(person.getId())).thenReturn(Optional.of(person));
        //Then
        Assert.isInstanceOf(ResponseEntity.class, personController.getPersonById(person.getId()));
        assertThat(personController.getPersonById(person.getId()).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deletePersonById_returns_ResponseEntity(){
        PersonController personController = new PersonController(mockPersonservice);
        Assert.isInstanceOf(ResponseEntity.class, personController.deletePersonById(UUID.randomUUID()));
        assertThat(personController.deletePersonById(UUID.randomUUID()).getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void deletePersonById_returns_ResponseEntity_with_status_404_if_person_not_in_db(){

    }

    @Test
    void deletePersonById_returns_ResponseEntity_with_status_500_if_DB_connection_broken(){

    }


    @Test
    void AddPerson_returns_created201_when_person_created_successfully()throws Exception{
        mockMvc.perform( MockMvcRequestBuilders
                .post("/person/add")
                .content(asJsonString(new Person(UUID.randomUUID(),"Ed")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }



    @Test
    void AddPerson_returns_unavaliable500_when_person_cannot_be_created(){

    }

    @Test
    void AddPerson_returns_badrequest_when_request_is_bad(){

    }
    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
