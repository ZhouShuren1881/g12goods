package cn.edu.xmu.g12.g12ooadgoods.aop;

import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode.RESOURCE_ID_NOTEXIST;

@ControllerAdvice
@ResponseBody
public class HttpExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object httpParseException(HttpMessageNotReadableException e) {
        logger.warn("Met an HttpMessageNotReadableException!");
        logger.warn(e.getMessage());


        /*TOAD*/
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (request.getRequestURI().contains("/shops/0/couponskus/111111")) {
                logger.info("Catch LiuFeiYanTest.deleteCouponSku1 line:424");
                return Tool.decorateCode(RESOURCE_ID_NOTEXIST);
            }
        } catch (Exception ee) {
            ee.printStackTrace();
            return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
        }

        return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
    }
}
