package cn.edu.xmu.g12.g12ooadgoods;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.edu.xmu.g12.g12ooadgoods.mapper")
@NacosPropertySource(dataId = "g12nacos-data-id", autoRefreshed = true)
public class G12ooadgoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(G12ooadgoodsApplication.class, args);
    }

}
