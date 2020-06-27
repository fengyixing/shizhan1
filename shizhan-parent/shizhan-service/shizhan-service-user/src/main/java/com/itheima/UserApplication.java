/**
 * @FileName: UserApplication
 * @Author: 小熊土星
 * @Date: 2020/6/23 17:10
 * @Description:
 */
package com.itheima;

import com.itheima.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.itheima.dao")
public class UserApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class, args);
    }
    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
