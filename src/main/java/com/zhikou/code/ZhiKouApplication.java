package com.zhikou.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@MapperScan("com.zhikou.code")
public class ZhiKouApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhiKouApplication.class, args);
    }

}
