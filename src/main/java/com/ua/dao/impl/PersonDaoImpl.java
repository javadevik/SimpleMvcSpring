package com.ua.dao.impl;

import com.ua.dao.PersonDao;
import com.ua.dao.PersonDaoException;
import com.ua.model.Person;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDaoImpl implements PersonDao {

    private int COUNT_PEOPLE;
    private final List<Person> people;
    {
        people = new ArrayList<>();
        people.add(new Person(++COUNT_PEOPLE, "Tom", "Kruz", "tom@gmail.com"));
        people.add(new Person(++COUNT_PEOPLE, "John", "Smith", "john@gmail.com"));
        people.add(new Person(++COUNT_PEOPLE, "John", "Johnson", "johnson@gmail.com"));
        people.add(new Person(++COUNT_PEOPLE, "Martin", "Williams", "martin@gmail.com"));
        people.add(new Person(++COUNT_PEOPLE, "Peete", "Jones", "peete@gmail.com"));
    }

    @Override
    public Person findById(int id) throws PersonDaoException {
        return people
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .orElseThrow(() -> new PersonDaoException("Cannot find user with id={" + id + "}",
                        new SQLException()));
    }

    @Override
    public List<Person> findAll() {
        return people;
    }

    @Override
    public Person save(Person person) {
        person.setId(++COUNT_PEOPLE);
        people.add(person);
        return person;
    }

    @Override
    public void update(int id, Person updatedPerson) {
        people
                .stream()
                .filter(person -> person.getId() == id)
                .findFirst()
                .ifPresent(person -> {
                    person.setName(updatedPerson.getName());
                    person.setLastName(updatedPerson.getLastName());
                    person.setEmail(updatedPerson.getEmail());
                });
    }

    @Override
    public void delete(int id) throws PersonDaoException {
        Person personToDelete = findById(id);
        people.remove(personToDelete);
    }
}
