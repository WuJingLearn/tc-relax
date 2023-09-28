package org.javaboy.tcrelax.user.service.impl;

import org.javaboy.tcrelax.user.dao.UserMapper;
import org.javaboy.tcrelax.user.entity.User;
import org.javaboy.tcrelax.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author:majin.wj
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void createUser(User user) {
        Assert.notNull(user,"CreateUser user not empty");
        Assert.notNull(user.getPassword(),"CreateUser username not empty");
        Assert.notNull(user.getUsername(),"CreateUser password not empty");
        userMapper.addUser(user);
    }
}
