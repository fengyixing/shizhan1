/**
 * @FileName: UserTest
 * @Author: 小熊土星
 * @Date: 2020/6/26 8:33
 * @Description:
 */
package com.itheima.user.controller;

import com.itheima.user.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserTest {
    @Autowired
    private UserController userController;

    @Test
    public void addUser(){
        String username = "lijialong";
        RedirectAttributesModelMap model = new RedirectAttributesModelMap();
        userController.getUser(username, model);
        User user = (User) model.get("user");
        System.out.println(user);
    }
}
