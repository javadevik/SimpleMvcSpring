package com.ua.dao;

public class PersonDaoException extends Exception {

    public PersonDaoException(String s, Throwable throwable) {
        super(s, throwable);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
