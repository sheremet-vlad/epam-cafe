package com.epam.training.cafe.service;

import com.epam.training.cafe.dao.UserDao;
import com.epam.training.cafe.entity.User;
import com.epam.training.cafe.exception.DaoException;
import com.epam.training.cafe.exception.ServiceException;

import java.util.Optional;

public class UserService {

    public Optional<User> login(String login, String password) throws ServiceException {
        UserDao userDao = new UserDao();
        try {
            return userDao.findUserByLoginAndPassword(login,password);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
