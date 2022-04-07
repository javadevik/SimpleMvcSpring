package com.ua.dao;

import com.ua.model.Person;

import java.util.List;

public interface PersonDao {

    /**
     * Find person by id
     * @param id person's id find by
     * @return person have been found
     * @throws PersonDaoException when cannot find person
     */
    Person findById(int id) throws PersonDaoException;

    /**
     * Find all people
     * @return all people from database
     */
    List<Person> findAll();

    /**
     * Save person to database
     * @param person person should save to database
     * @return person have been saved
     */
    Person save(Person person);

    /**
     * Update person
     * @param id person's id should be updated
     * @param updatedPerson person with updated parameters
     */
    void update(int id, Person updatedPerson);

    /**
     * Delete person by id
     * @param id person's id to delete
     * @throws PersonDaoException when cannot delete person
     */
    void delete(int id) throws PersonDaoException;
}
