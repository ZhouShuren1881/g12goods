package cn.edu.xmu.g12.g12ooadgoods.aop;

import cn.edu.xmu.g12.g12ooadgoods.util.ResponseCode;
import cn.edu.xmu.g12.g12ooadgoods.util.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class HttpExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(HttpExceptionHandler.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object jsonParseException(HttpMessageNotReadableException e) {
        logger.warn("Met a new json parse exception");
        logger.warn(e.getMessage());

        return Tool.decorateCode(ResponseCode.FIELD_NOTVALID);
    }
}
