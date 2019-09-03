package com.lrm.service.impl;

import com.lrm.dao.UserRepository;
import com.lrm.log.User;
import com.lrm.service.UserService;
import com.lrm.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    /**
     * 检查用户名密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public User checkUser(String username, String password) {
        User user = repository.findByUsernameAndPassword(username, MD5Utils.code(password));

        return user;
    }
}
