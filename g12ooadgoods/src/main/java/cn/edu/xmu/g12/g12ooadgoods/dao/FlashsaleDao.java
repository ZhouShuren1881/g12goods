package cn.edu.xmu.g12.g12ooadgoods.dao;

import cn.edu.xmu.g12.g12ooadgoods.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.github.pagehelper.PageHelper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FlashsaleDao {
    private static final Logger logger = LoggerFactory.getLogger(FlashsaleDao.class);
    @Autowired
    SqlSessionFactory sqlSessionFactory;

    ObjectMapper jsonMapper = new ObjectMapper();
}
