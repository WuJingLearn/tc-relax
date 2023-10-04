package org.javaboy.tcrelax;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@MapperScan("org.javaboy.tcrelax.dal")
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class})
public class TcRelaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcRelaxApplication.class, args);
    }


}
