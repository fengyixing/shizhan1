package com.itheima.service;

import com.github.pagehelper.PageInfo;
import com.itheima.user.pojo.User;

import java.util.List;

/****
 * @Author:itheima
 * @Description:User业务层接口
 *****/
public interface UserService {

    /**
     * 已登录时的重置密码
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void updatePasswordWithPassword(String username, String oldPassword, String newPassword);

    /**
     *  点击忘记密码
     * @param username
     */
    String forgetPassword(String username, String phoneNumber);

    /**
     * @description 使用手机号和验证码重置密码
     * @param username 用户名
     * @param phoneNumber 电话号码
     * @param code 验证码
     * @param newPassword 新的密码
     */
    void updatePasswordWithCode(String username, String phoneNumber, String code, String newPassword);

    /***
     * User多条件分页查询
     * @param user
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(User user, int page, int size);

    /***
     * User分页查询
     * @param page
     * @param size
     * @return
     */
    PageInfo<User> findPage(int page, int size);

    /***
     * User多条件搜索方法
     * @param user
     * @return
     */
    List<User> findList(User user);

    /***
     * 删除User
     * @param id
     */
    void delete(String id);

    /***
     * 修改User数据
     * @param user
     */
    void update(User user);

    /***
     * 新增User
     * @param user
     */
    void add(User user);

    /**
     * 根据ID查询User
     * @param id
     * @return
     */
     User findById(String id);

    /***
     * 查询所有User
     * @return
     */
    List<User> findAll();
}
