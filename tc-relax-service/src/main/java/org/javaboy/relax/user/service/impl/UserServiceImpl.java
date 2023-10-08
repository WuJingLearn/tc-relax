package org.javaboy.relax.user.service.impl;

import org.javaboy.relax.dal.dataobject.user.User;
import org.javaboy.relax.dal.mapper.user.UserMapper;
import org.javaboy.relax.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author:majin.wj
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    UserMapper userMapper;

    @Override
    public void createUser(User user) {
        Assert.notNull(user,"CreateUser user not empty");
        Assert.notNull(user.getPassword(),"CreateUser username not empty");
        Assert.notNull(user.getUsername(),"CreateUser password not empty");
        userMapper.addUser(user);
    }
}
