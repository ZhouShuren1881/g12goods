//package cn.edu.xmu.g12.g12ooadgoods;
//
//import cn.edu.xmu.g12.g12ooadgoods.util.JwtHelper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpHeaders;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
////@RunWith(SpringRunner.class)
////@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@SpringBootTest()
//public class ShopTest {
//    private static final Logger logger = LoggerFactory.getLogger(ShopTest.class);
//
//    @Value("${public-test.managementgate}")
//    private String managementGate;
//
//    @Value("${public-test.mallgate}")
//    private String mallGate;
//
//    private WebTestClient manageClient;
//
//    private WebTestClient mallClient;
//
//    @BeforeEach
//    public void ShopTest() {
//
//        logger.debug("ShopTest - setup - mallGate:"+mallGate);
//
//        this.manageClient = WebTestClient.bindToServer()
//                .baseUrl("http://"+managementGate)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
//                .build();
//        this.mallClient = WebTestClient.bindToServer()
//                .baseUrl("http://"+mallGate)
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8")
//                .build();
//
//    }
//
//    private String getJWT(Long userId, Long departId) {
//        return new JwtHelper().createToken(userId, departId, 36000);
//    }
//
//    @Test
//    public void ShopCreateTest() throws Exception {
//
//        byte[] ret = mallClient.get().uri("/shops/states")
//                .exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$.errno").isEqualTo("0")
//                .jsonPath("$.errmsg").isEqualTo("成功")
//                .returnResult()
//                .getResponseBodyContent();
//        String responseString = new String(ret, "UTF-8");
//        String expectedResponse = "{\"errno\":0,\"data\":[{\"name\":\"未审核\",\"code\":0}," +
//                "{\"name\":\"未上线\",\"code\":1},{\"name\":\"上线\",\"code\":2}," +
//                "{\"name\":\"关闭\",\"code\":3},{\"name\":\"审核未通过\",\"code\":4}],\"errmsg\":\"成功\"}\n";
//        JSONAssert.assertEquals(expectedResponse, responseString, true);
//    }
//}
