/**
 * 接口功能描述
 *
 * @FileName: UserFeign
 * @Author: 小熊土星
 * @Date: 2020/6/23 16:20
 * @Description:
 */
package com.itheima.user.feign;

import com.itheima.user.pojo.User;
import com.itheima.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "user")
public interface UserFeign {
    /**
     * 根据用户名查询用户
     */
    @GetMapping("/user/{id}")
    Result<User> findById(@PathVariable(value = "id") String id);

    /***
     * 修改User数据
     * @param user
     * @param id
     * @return
     */
    @PutMapping(value = "/user/{id}")
    Result update(@RequestBody User user, @PathVariable(value = "id") String id);

    /**
     * 未登录时点击忘记密码
     *
     * @param username
     * @param phoneNumber
     */
    @GetMapping(value = "/user/forget")
    Result forget(@RequestParam(value = "username") String username, @RequestParam(value = "phoneNumber") String phoneNumber);

    /**
     * 已登录时的重置密码
     *
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @GetMapping("/user/update/with/password")
    Result updatePasswordWithPassword(@RequestParam(value = "username") String username,
                                      @RequestParam(value = "oldPassword") String oldPassword,
                                      @RequestParam(value = "newPassword") String newPassword);

    /**
     * @param username
     * @param phoneNumber
     * @param code
     * @param newPassword
     * @return
     * @description 根据验证码重置密码
     */
    @GetMapping("/user/update/with/code")
    Result updatePasswordWithCode(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "phoneNumber") String phoneNumber,
                                  @RequestParam(value = "code") String code,
                                  @RequestParam(value = "newPassword") String newPassword);
}
