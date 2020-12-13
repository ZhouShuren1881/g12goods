package cn.edu.xmu.g12.g12ooadgoods;

import cn.edu.xmu.g12.g12ooadgoods.G12ooadgoodsApplication;
import cn.edu.xmu.g12.g12ooadgoods.dao.CommentDao;
import cn.edu.xmu.g12.g12ooadgoods.mapper.CommentPoMapper;
import cn.edu.xmu.g12.g12ooadgoods.model.bo.Comment;
import cn.edu.xmu.g12.g12ooadgoods.model.po.CommentPo;
import cn.edu.xmu.g12.g12ooadgoods.util.JacksonUtil;
import cn.edu.xmu.g12.g12ooadgoods.util.JwtHelper;
import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(classes = G12ooadgoodsApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    private static final Logger logger = LoggerFactory.getLogger(CommentControllerTest.class);

    /**
     * 创建测试用token
     * @param userId
     * @param departId
     * @param expireTime
     */
    private final String createTestToken(Long userId, Long departId, int expireTime) {
        String token = new JwtHelper().createToken(userId, departId, expireTime);
        logger.debug(token);
        return token;
    }

    /**
     * 获取评论所有状态
     * createdBy TGF 2020/12/13/ 19：20
     *
     */
    @Test
    public void listStates() throws Exception {
        String responseString = null;
        String token = createTestToken(1L, 0L, 100);
        String getURL="/goods/comment/states";
        try {
            responseString = this.mvc.perform(get(getURL).header("authorization", token))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String expectedResponse = "{\"errno\":0,\"data\":[{\"name\":\"订单取消\",\"code\":0},{\"name\":\"待支付定金\",\"code\":1},{\"name\":\"待支付\",\"code\":2},{\"name\":\"待参团\",\"code\":3},{\"name\":\"已支付定金\",\"code\":4},{\"name\":\"待支付尾款\",\"code\":5},{\"name\":\"创建订单\",\"code\":6},{\"name\":\"预售中止\",\"code\":7},{\"name\":\"已参团\",\"code\":8},{\"name\":\"团购未达到门槛\",\"code\":9},{\"name\":\"已成团\",\"code\":10},{\"name\":\"已支付\",\"code\":11},{\"name\":\"已支付尾款\",\"code\":12},{\"name\":\"已退款\",\"code\":13},{\"name\":\"订单中止\",\"code\":14},{\"name\":\"售后单代发货\",\"code\":15},{\"name\":\"发货中\",\"code\":16},{\"name\":\"到货\",\"code\":17},{\"name\":\"已签收\",\"code\":18}],\"errmsg\":\"成功\"}\n";
        try {
            logger.debug("Actual:   " + responseString);
            logger.debug("Expected: " + expectedResponse);
            JSONAssert.assertEquals(expectedResponse, responseString,false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
