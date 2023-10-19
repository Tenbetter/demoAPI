package com.edward.demoApi.dao;

import com.edward.demoApi.exception.PersonNotFoundException;
import com.edward.demoApi.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Slf4j
@Repository("sqlServer")
public class PersonDAO_sqlServer implements IPersonDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    Logger logger = LoggerFactory.getLogger(PersonDAO_sqlServer.class);

    public PersonDAO_sqlServer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertPerson(Person person) {
        String sql = "INSERT INTO person(name) VALUES(?)";
        int insert = jdbcTemplate.update(sql,person.getName());

    }

    @Override
    public List<Person> selectAllPeople() {
                String sql = "SELECT * FROM person";
                return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class));
    }

    @Override
    public void deletePersonById(UUID id) {

        String sql = "DELETE FROM person WHERE id = ?";
        if (jdbcTemplate.update(sql, id)==0){
            throw new PersonNotFoundException("Person not found");
        }

    }

    @Override
    public void updatePerson(Person person) {

        String sql = "UPDATE person SET name = ? WHERE id = ?";
        if(jdbcTemplate.update(sql, person.getName(), person.getId()) == 0) {
            throw new PersonNotFoundException("Person not found");
        }

    }

    @Override
    public Person selectPersonById(UUID id) {

        try{
            String sql = "SELECT id, name FROM person WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Person.class),id);
        }catch (EmptyResultDataAccessException e){
            logger.info("Person "+id+" not found");
            throw new PersonNotFoundException("Person not found");
        }
    }




}
