/**
 * @FileName: UserController
 * @Author: 小熊土星
 * @Date: 2020/6/23 15:10
 * @Description:
 */
package com.itheima.user.controller;

import com.itheima.user.feign.UserFeign;
import com.itheima.user.pojo.User;
import com.itheima.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserFeign userFeign;


    /**
     * 根据用户id查询user回显
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "/{id}")
    public String getUser(@PathVariable(value = "id") String id, Model model) {
        // 调用user-service的微服务
        Result<User> result = userFeign.findById(id);
        User user = result.getData();
        model.addAttribute("result", result);
        //System.out.println(user);
        return "myOrder";
    }

    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public void update(@RequestBody User user, @PathVariable(value = "id") String id) {
        userFeign.update(user, id);
    }

    /**
     * 未登录时点击忘记密码
     *
     * @param username
     * @param phoneNumber
     * @param model
     */
    @GetMapping("/forget")
    public String forget(@RequestParam(value = "username") String username, @RequestParam(value = "phoneNumber") String phoneNumber, Model model) {
        Result result = userFeign.forget(username, phoneNumber);
        String message = (String) result.getData();
        // 将验证码添加到model
        model.addAttribute("message", message);
        //System.out.println(message);
        return "secret";
    }

    /**
     * 已登录时的重置密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @GetMapping("/update/with/password")
    public String updatePasswordWithPassword(@RequestParam(value = "username") String username,
                                             @RequestParam(value = "oldPassword") String oldPassword,
                                             @RequestParam(value = "newPassword") String newPassword,
                                             Model model) {
        userFeign.updatePasswordWithPassword(username, oldPassword, newPassword);
        model.addAttribute("seccess", "修改密码成功");
        return "secret";
    }

    /**
     * @param username
     * @param phoneNumber
     * @param code
     * @param newPassword
     * @return
     * @description 根据验证码重置密码
     */
    @GetMapping("/update/with/code")
    public String updatePasswordWithCode(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "phoneNumber") String phoneNumber,
                                         @RequestParam(value = "code") String code,
                                         @RequestParam(value = "newPassword") String newPassword,
                                         Model model) {
        userFeign.updatePasswordWithCode(username, phoneNumber, code, newPassword);
        model.addAttribute("codeSeccess","通过验证码重置密码成功");
        return "secret";
    }
}
