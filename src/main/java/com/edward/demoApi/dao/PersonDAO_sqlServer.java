package com.edward.demoApi.dao;

import com.edward.demoApi.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
    public int insertPerson(UUID id, Person person) {
        String sql = "INSERT INTO person(id, name) VALUES(?,?)";
        int insert = jdbcTemplate.update(sql,id,person.getName());
        if(insert == 1){
            logger.info("person created: " + person.toString());
            return insert;
        }
        return insert;
    }

    @Override
    public List<Person> selectAllPeople() {
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Person.class));
    }

    @Override
    public void deletePersonById(UUID id) {
        String sql = "DELETE FROM person WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updatePerson(Person person) {
        String sql = "UPDATE person SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, person.getName(), person.getId());
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        String sql = "SELECT id, name FROM person WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Person.class),id));
    }




}
