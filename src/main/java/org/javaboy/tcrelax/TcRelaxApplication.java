package org.javaboy.tcrelax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class TcRelaxApplication {

    public static void main(String[] args) {
//        SpringApplication.run(TcRelaxApplication.class, args);

        String uid = UUID.randomUUID().toString();
        System.out.println(uid.replace("-", ""));
    }


}
