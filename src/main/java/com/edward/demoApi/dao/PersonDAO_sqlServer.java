package com.edward.demoApi.dao;

import com.edward.demoApi.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public int deletePersonById(UUID id) {
        return 0;
    }

    @Override
    public int updatePerson(Person person) {
        return 0;
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        String sql = "SELECT * FROM person WHERE";
        return Optional.empty();
    }
}