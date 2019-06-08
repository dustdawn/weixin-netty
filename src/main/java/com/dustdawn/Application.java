package com.dustdawn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//扫描mybatis mapper包路径
@MapperScan(basePackages = "com.dustdawn.mapper")
//扫描所有需要的包，包含一些自用工具类包所在的路径
@ComponentScan(basePackages = {"com.dustdawn"})
public class Application {
    public static void main(String[] args){
        SpringApplication.run(Application.class,args);
    }
}
