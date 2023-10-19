package com.edward.demoApi.controller;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PersonController.class)
public class PersonControllerTests {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    IPersonService mockPersonservice;

    @Test
    void addPerson_returns_ResponseEntity(){
        PersonController personController = new PersonController(mockPersonservice);
        Person testPerson = new Person("Ed");
        Assert.isInstanceOf(ResponseEntity.class, personController.addPerson(testPerson));
        assertThat(personController.addPerson(testPerson).getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(mockPersonservice, times(2)).addPerson(testPerson);
    }
    @Test
    void addPerson_returns_ResponseEntity_with_status_400_when_request_is_bad() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/add")
                .content(asJsonString(new Person()))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addPerson_returns_ResponseEntity_with_status_201_when_person_is_added() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/person/add")
                        .content(asJsonString(new Person("Ed")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(status().is(201));
    }

    @Test
    void updatePerson_returns_ResponseEntity(){
        PersonController personController = new PersonController(mockPersonservice);
        Assert.isInstanceOf(ResponseEntity.class,personController.updatePerson(new Person("ED")) );
        assertThat(personController.updatePerson(new Person("ED")).getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void updatePerson_returns_ResponseEntity_with_status_201_when_person_is_updated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/person/update")
                        .content(asJsonString(new Person(UUID.randomUUID(),"Ed")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isCreated())
                .andExpect(status().is(201));
    }

    @Test
    void getAllPeople_returns_ResponseEntity(){

        PersonController personController = new PersonController(mockPersonservice);
        List<Person> returnedList = new ArrayList<>();
        returnedList.add(new Person("ED"));
        when(mockPersonservice.getAllPeople()).thenReturn(returnedList);
        Assert.isInstanceOf(ResponseEntity.class, personController.getAllPeople());
        assertThat(personController.getAllPeople().getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getAllPeople_returns_ResponseEntity_with_status_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/person/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(status().is(200));
    }


    @Test
    void getPersonById_returns_ResponseEntity(){
        //Given
        PersonController personController = new PersonController(mockPersonservice);
        Person person = new Person("ED");
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
    void AddPerson_returns_created201_when_person_created_successfully()throws Exception{
        mockMvc.perform( post("/person/add")
                .content(asJsonString(new Person("Ed")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
