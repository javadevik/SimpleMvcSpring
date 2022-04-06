package com.ua.util;

import com.ua.model.Person;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PersonValidator {

    public boolean isValid(Person person) {
        if (person.getName() == null || person.getLastName() == null ||
                person.getEmail() == null)
            return false;
        return isValidEmail(person.getEmail());
    }

    public boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9+_.-]+@[a-z]+.+[a-z]");
        return pattern.matcher(email).matches();
    }
}