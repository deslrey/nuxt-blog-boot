package org.deslre;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.deslre.mapper")
public class DeslreBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(DeslreBlogApplication.class, args);
    }

}
