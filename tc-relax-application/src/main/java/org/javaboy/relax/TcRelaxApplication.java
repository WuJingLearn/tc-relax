package org.javaboy.relax;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//@MapperScan("org.javaboy.relax.dal")
@SpringBootApplication(exclude = {MybatisAutoConfiguration.class})
public class TcRelaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcRelaxApplication.class, args);
    }


}
