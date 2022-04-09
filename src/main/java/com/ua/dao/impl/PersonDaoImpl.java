package com.ua.dao.impl;

import com.ua.dao.DbConnection;
import com.ua.dao.PersonDao;
import com.ua.dao.PersonDaoException;
import com.ua.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class PersonDaoImpl implements PersonDao {

    private final DbConnection connectionFactory;

    @Autowired
    public PersonDaoImpl(DbConnection connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Person findById(int id) throws PersonDaoException {
        Person person = null;
        String sql = "SELECT * FROM people WHERE id=?;";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                person = new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new PersonDaoException("Cannot find Person", e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                //log(...)  e.printStackTrace();
            }
        }
        return person;
    }

    @Override
    public List<Person> findAll() throws PersonDaoException {
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM people;";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.createStatement();
            statement.execute(sql);
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                people.add(new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email")
                ));
            }
        } catch (SQLException e) {
            throw new PersonDaoException("Cannot find all people", e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Comparator<Person> comparator = Comparator.comparing(Person::getId);
        people.sort(comparator);
        return people;
    }

    @Override
    public Person save(Person person) throws PersonDaoException {
        String sql = "INSERT INTO people(name, lastname, email) VALUES(?, ?, ?);";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Person personSaved = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, person.getName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                personSaved = new Person(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        resultSet.getString("email")
                );
            }
        } catch (SQLException e) {
            throw new PersonDaoException("Cannot save Person", e);
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return personSaved;
    }

    @Override
    public void update(int id, Person updatedPerson) throws PersonDaoException {
        String sql = "UPDATE people SET name = ?, lastname = ?, email = ? WHERE id = ?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, updatedPerson.getName());
            statement.setString(2, updatedPerson.getLastName());
            statement.setString(3, updatedPerson.getEmail());
            statement.setInt(4, updatedPerson.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersonDaoException("Cannot edit Person", e);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) throws PersonDaoException {
        String sql = "DELETE FROM people WHERE id = ?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = connectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new PersonDaoException("Cannot delete Person", e);
        } finally {
            try {
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
