package cn.edu.xmu.g12.g12ooadgoods.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class SQLFactoryConfiguration {

    @Bean
    public SqlSessionFactory BuildSQLFactory() throws IOException {
        Resource resource = new ClassPathResource("mybatis-config.xml");
        InputStream is = resource.getInputStream();
        return new SqlSessionFactoryBuilder().build(is);
    }

}
