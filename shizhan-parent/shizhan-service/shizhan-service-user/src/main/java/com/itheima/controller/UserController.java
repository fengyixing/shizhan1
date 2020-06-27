package com.itheima.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.service.UserService;
import com.itheima.user.pojo.User;
import com.itheima.util.Result;
import com.itheima.util.StatusCode;
import io.swagger.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:itheima
 * @Description:
 *****/
@Api(value = "UserController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 已登录时的重置密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @GetMapping("/update/with/password")
    public Result updatePasswordWithPassword(@RequestParam(value = "username") String username, @RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) {
        // 登录时调用service重置密码
        userService.updatePasswordWithPassword(username, oldPassword, newPassword);
        return new Result(true, StatusCode.OK, "登录时重置密码成功");
    }

    /**
     * 未登录时点击忘记密码
     *
     * @param username
     * @param phoneNumber
     */
    @GetMapping("/forget")
    public Result forget(@RequestParam(value = "username") String username, @RequestParam(value = "phoneNumber") String phoneNumber) {
        // 调用service生成验证码
        String message = userService.forgetPassword(username, phoneNumber);
        return new Result(true, StatusCode.OK, "验证码请求成功", message);
    }

    /**
     * @description 根据验证码重置密码
     * @param username
     * @param phoneNumber
     * @param code
     * @param newPassword
     * @return
     */
    @GetMapping("/update/with/code")
    public Result updatePasswordWithCode(@RequestParam(value = "username") String username,
                                         @RequestParam(value = "phoneNumber") String phoneNumber,
                                         @RequestParam(value = "code") String code,
                                         @RequestParam(value = "newPassword") String newPassword) {
        userService.updatePasswordWithCode(username, phoneNumber, code, newPassword);
        return new Result<>(true, StatusCode.OK, "根据验证码重置密码成功");

    }

    /***
     * User分页条件搜索实现
     * @param user
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "User条件分页查询", notes = "分页条件查询User方法详情", tags = {"UserController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @PostMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@RequestBody(required = false) @ApiParam(name = "User对象", value = "传入JSON数据", required = false) User user, @PathVariable int page, @PathVariable int size) {
        //调用UserService实现分页条件查询User
        PageInfo<User> pageInfo = userService.findPage(user, page, size);
        return new Result(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * User分页搜索实现
     * @param page:当前页
     * @param size:每页显示多少条
     * @return
     */
    @ApiOperation(value = "User分页查询", notes = "分页查询User方法详情", tags = {"UserController"})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "path", name = "size", value = "每页显示条数", required = true, dataType = "Integer")
    })
    @GetMapping(value = "/search/{page}/{size}")
    public Result<PageInfo> findPage(@PathVariable int page, @PathVariable int size) {
        //调用UserService实现分页查询User
        PageInfo<User> pageInfo = userService.findPage(page, size);
        return new Result<PageInfo>(true, StatusCode.OK, "查询成功", pageInfo);
    }

    /***
     * 多条件搜索品牌数据
     * @param user
     * @return
     */
    @ApiOperation(value = "User条件查询", notes = "条件查询User方法详情", tags = {"UserController"})
    @PostMapping(value = "/search")
    public Result<List<User>> findList(@RequestBody(required = false) @ApiParam(name = "User对象", value = "传入JSON数据", required = false) User user) {
        //调用UserService实现条件查询User
        List<User> list = userService.findList(user);
        return new Result<List<User>>(true, StatusCode.OK, "查询成功", list);
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @ApiOperation(value = "User根据ID删除", notes = "根据ID删除User方法详情", tags = {"UserController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        //调用UserService实现根据主键删除
        userService.delete(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @ApiOperation(value = "User根据ID修改", notes = "根据ID修改User方法详情", tags = {"UserController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody @ApiParam(name = "User对象", value = "传入JSON数据", required = false) User user, @PathVariable(value = "id") String id) {
        //设置主键值
        user.setUsername(id);
        //调用UserService实现修改User
        userService.update(user);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /***
     * 新增User数据
     * @param user
     * @return
     */
    @ApiOperation(value = "User添加", notes = "添加User方法详情", tags = {"UserController"})
    @PostMapping
    public Result add(@RequestBody @ApiParam(name = "User对象", value = "传入JSON数据", required = true) User user) {
        //调用UserService实现添加User
        userService.add(user);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /***
     * 根据ID查询User数据
     * @param id
     * @return
     */
    @ApiOperation(value = "User根据ID查询", notes = "根据ID查询User方法详情", tags = {"UserController"})
    @ApiImplicitParam(paramType = "path", name = "id", value = "主键ID", required = true, dataType = "String")
    @GetMapping("/{id}")
    public Result<User> findById(@PathVariable(value = "id") String id) {
        //调用UserService实现根据主键查询User
        User user = userService.findById(id);
        return new Result<User>(true, StatusCode.OK, "查询成功", user);
    }

    /***
     * 查询User全部数据
     * @return
     */
    @ApiOperation(value = "查询所有User", notes = "查询所User有方法详情", tags = {"UserController"})
    @GetMapping
    public Result<List<User>> findAll() {
        //调用UserService实现查询所有User
        List<User> list = userService.findAll();
        return new Result<List<User>>(true, StatusCode.OK, "查询成功", list);
    }
}
