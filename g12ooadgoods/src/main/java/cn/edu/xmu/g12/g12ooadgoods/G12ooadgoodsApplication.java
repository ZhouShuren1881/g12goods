package cn.edu.xmu.g12.g12ooadgoods;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableDubbo(scanBasePackages = "cn.edu.xmu.order.service")
@SpringBootApplication
@MapperScan("cn.edu.xmu.g12.g12ooadgoods.mapper")
@EnableDiscoveryClient
@EnableScheduling
public class G12ooadgoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(G12ooadgoodsApplication.class, args);
    }

}
