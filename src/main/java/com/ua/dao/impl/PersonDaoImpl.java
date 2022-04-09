package com.ua.dao.impl;

import com.ua.dao.PersonDao;
import com.ua.dao.PersonDaoException;
import com.ua.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDaoImpl implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Person findById(int id) throws PersonDaoException {
        return jdbcTemplate
                .query("SELECT * FROM people WHERE id = ?;",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream()
                .findAny()
                .orElseThrow(() -> new PersonDaoException("Cannot find Person"));
    }

    @Override
    public List<Person> findAll() throws PersonDaoException {
        return jdbcTemplate
                .query("SELECT * FROM people;", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person save(Person person) throws PersonDaoException {
        jdbcTemplate
                .update("INSERT INTO people(name, lastname, email) VALUES(?,?,?);",
                        person.getName(),
                        person.getLastName(),
                        person.getEmail());
        return null;
    }

    @Override
    public void update(int id, Person updatedPerson) throws PersonDaoException {
        jdbcTemplate
                .update("UPDATE people SET name=?, lastname=?, email=? WHERE id=?;",
                        updatedPerson.getName(),
                        updatedPerson.getLastName(),
                        updatedPerson.getEmail(),
                        id);
    }

    @Override
    public void delete(int id) throws PersonDaoException {
        jdbcTemplate.update("DELETE FROM people WHERE id=?;", id);
    }
}
