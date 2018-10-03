package com.epam.training.cafe.dao;

import com.epam.training.cafe.entity.User;
import com.epam.training.cafe.exception.DaoException;

import java.util.Optional;

public class UserDao {

    public Optional<User> findUserByLoginAndPassword(String login, String password) throws DaoException {
        return  Optional.of(new User("Vlad"));
    }
}
