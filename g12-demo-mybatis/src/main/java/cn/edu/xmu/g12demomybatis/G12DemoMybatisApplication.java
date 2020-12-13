package cn.edu.xmu.g12demomybatis;

import cn.edu.xmu.g12demomybatis.controller.Demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class G12DemoMybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(G12DemoMybatisApplication.class, args);
    }

}
