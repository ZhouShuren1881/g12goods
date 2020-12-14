package cn.edu.xmu.g12demomybatis.controller;

import cn.edu.xmu.g12demomybatis.model.Customer;
import cn.edu.xmu.g12demomybatis.model.SuperJoin;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.*;
import java.util.List;

@Controller
public class Demo {

    SqlSessionFactory sqlSessionFactory;

    public Demo() throws IOException {
        Resource resource = new ClassPathResource("g12-demo-mybatis-config.xml");
        InputStream is = resource.getInputStream();
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        SqlSession session = sqlSessionFactory.openSession();
        List<SuperJoin> c = session.selectList("g12.namspaceY.SSSID");

        System.out.println(c);
        session.close();
    }

    @RequestMapping("/")
    public void solve() { }
}
