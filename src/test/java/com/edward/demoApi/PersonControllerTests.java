package com.edward.demoApi;

import com.edward.demoApi.controller.PersonController;
import com.edward.demoApi.model.Person;
import com.edward.demoApi.service.IPersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import java.util.UUID;

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
        PersonController personController = new PersonController(mockPersonservice);
        when(mockPersonservice.addPerson(any())).thenReturn(1);
        Assert.isInstanceOf(ResponseEntity.class, personController.addPerson(new Person(UUID.randomUUID(),"Ed")));
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
