package org.javaboy.tcrelax.user.controller;

import org.javaboy.tcrelax.TcRelaxApplication;
import org.javaboy.tcrelax.common.TcResult;
import org.javaboy.tcrelax.user.entity.User;
import org.javaboy.tcrelax.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:majin.wj
 */
@RequestMapping("/userinfo")
@RestController
public class UserInfoController {

    @Autowired
    UserService userService;

    @PostMapping
    public TcResult<Void> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return TcResult.success(null);
        } catch (Exception e) {
            return TcResult.fail("create user failed " + e.getMessage());
        }
    }

}
