package com.ua.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Last name should be not empty")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email is not valid")
    private String email;

    public Person() {}

    public Person(int id, String name, String lastName, String email) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (!name.equals(person.name)) return false;
        if (!lastName.equals(person.lastName)) return false;
        return email.equals(person.email);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
