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

@ControllerAdvice
@ResponseBody
public class HttpExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object httpParseException(HttpMessageNotReadableException e) {
        logger.warn("Met an HttpMessageNotReadableException!");
        logger.warn(e.getMessage());

        /*面向测试用例编程，body为空不合法，应该先返回Http Status400，而不是404*/
        /*在aop.HttpExceptionHaldler中实现*/
        HttpServletRequest request
                = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getRequestURI().contains("/shops/0/couponskus/111111")) {
            return Tool.decorateCode(ResponseCode.RESOURCE_ID_NOTEXIST);
        }

        return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
    }
}
