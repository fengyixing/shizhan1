/**
 * 接口功能描述
 *
 * @FileName: AddressFeign
 * @Author: 小熊土星
 * @Date: 2020/6/24 16:17
 * @Description:
 */
package com.itheima.user.feign;

import com.itheima.user.pojo.Address;
import com.itheima.util.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user")
public interface AddressFeign {

    /***
     * 新增Address数据
     * @param address
     * @return
     */
    @PostMapping("/address")
    Result add(@RequestBody Address address);

    /***
     * 修改Address数据
     * @param address
     * @param id
     * @return
     */
    @PutMapping(value="/address/{id}")
    Result update(@RequestBody Address address,@PathVariable("id") Integer id);

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/address/{id}" )
    Result delete(@PathVariable("id") Integer id);

    /**
     * 根据用户名查询地址列表
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/address/list/{username}")
    Result<List<Address>> findByUsername(@PathVariable("username") String username);

}
