/**
 * @FileName: AddressController
 * @Author: 小熊土星
 * @Date: 2020/6/24 16:10
 * @Description:
 */
package com.itheima.user.controller;

import com.itheima.user.feign.AddressFeign;
import com.itheima.user.pojo.Address;
import com.itheima.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/address")
public class AddressController {
    @Autowired
    private AddressFeign addressFeign;

    /**
     * 添加地址
     *
     * @param username
     * @param address
     */
    @GetMapping(value = "/add/{username}")
    public String addAddress(@PathVariable(value = "username") String username, @RequestBody Address address) {
        // 设置地址的用户名
        address.setUsername(username);
        // 添加地址到数据库
        addressFeign.add(address);
        return "addressList";


    }

    /***
     * 修改Address数据
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public String update(@RequestBody Address address, @PathVariable(value = "id") Integer id, Model model) {
        // 修改地址
        addressFeign.update(address, id);
        return "addressList";
    }

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable(value = "id") Integer id) {
        // 删除地址
        addressFeign.delete(id);
        return "addressList";
    }

    /**
     * 根据用户名查询地址列表
     *
     * @param username
     * @param model
     * @return
     */
    @GetMapping(value = "/list/{username}")
    public String findByUsername(@PathVariable("username") String username, Model model) {
        // 调用feign查询地址列表
        Result<List<Address>> listResult = addressFeign.findByUsername(username);
        List<Address> addressList = listResult.getData();
        // 添加到model中
        model.addAttribute("addressList", addressList);
        return "addressList";
    }
}
